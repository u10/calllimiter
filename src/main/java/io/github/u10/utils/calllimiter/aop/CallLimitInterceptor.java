package io.github.u10.utils.calllimiter.aop;

import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import com.google.common.cache.CacheBuilder;
import io.github.u10.utils.calllimiter.annotation.CallLimit;
import io.github.u10.utils.calllimiter.config.CallLimiterProperties;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CallLimitInterceptor implements MethodInterceptor {

	@Autowired
	private CallLimiterProperties callLimiterProperties;
	private Map<String, CallRecord> callHistory;
	private BlockingQueue<CallRecord> scheduleTasks = new LinkedBlockingQueue<>();

	private ExecutorService es;

	@PostConstruct
	private void init() {
		long cacheSize = callLimiterProperties.getLimitManager().getCacheSize();
		callHistory = CacheBuilder.newBuilder().maximumSize(cacheSize).<String, CallRecord>build().asMap();

		int poolSize = callLimiterProperties.getLimitManager().getPoolSize();
		es = poolSize <= 0 ? Executors.newCachedThreadPool() : Executors.newFixedThreadPool(poolSize);

		new Thread(() -> {
			int n = 0;
			while (true) {
				try {
					CallRecord record = scheduleTasks.take();
					final MethodInvocation invocation = record.invocation;
					long schedule = record.schedule;
					long now = System.currentTimeMillis();
					if (invocation == null) {
						// 跳过
					} else if (now > schedule) {
						es.execute(() -> {
							try {
								// 更新最后执行时间
								record.lastProceed = System.currentTimeMillis();
								// 释放函数调用对象
								record.invocation = null;
								// 执行代码
								invocation.proceed();
							} catch (Throwable e) {
								log.error("Proceed method failed: ", e);
							}
						});
					} else {
						// 放回等待下轮检查
						scheduleTasks.put(record);
					}
					if (n++ > scheduleTasks.size()) {
						n = 0;
						Thread.sleep(60);
					}
				} catch (Exception e) {
					log.error("Check failed: ", e);
				}
			}
		}).start();
	}

	private String genCallId(String callKey, final MethodInvocation invocation) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		if ("".equals(callKey)) {
			// 如果没有指定调用 key，使用方法的 hashCode 作为 Key
			mDigest.update(ByteBuffer.allocate(4).putInt(invocation.getMethod().hashCode()).array());
		} else {
			mDigest.update(callKey.getBytes());
		}
		for (Object argv : invocation.getArguments()) {
			mDigest.update(ByteBuffer.allocate(4).putInt(argv.hashCode()).array());
		}
		return Base64.getEncoder().encodeToString(mDigest.digest());
	}

	public synchronized Object invoke(final MethodInvocation invocation) throws Throwable {

		Method method = invocation.getMethod();
		CallLimit annotation = AnnotatedElementUtils.findMergedAnnotation(method, CallLimit.class);
		long debounce = annotation.debounce();
		long throttle = annotation.throttle();
		String callKey = annotation.key();
		String callId = annotation.id();

		if ("".equals(callId)) {
			callId = genCallId(callKey, invocation);
		}
		log.debug("limit call id: {}", callId);

		CallRecord record = callHistory.get(callId);
		Long now = System.currentTimeMillis();

		if (record == null) {
			callHistory.put(callId, record = new CallRecord());
		}

		if (now - record.lastProceed > throttle) {
			// 需要立即执行
			record.invocation = null;
			record.lastProceed = now;
			// 提交到线程池执行
			es.execute(() -> {
				try {
					invocation.proceed();
				} catch (Throwable e) {
					log.error("Proceed method failed: ", e);
				}
			});
		} else {
			// 计划执行
			if (record.invocation == null) {
				// 添加执行队列
				scheduleTasks.put(record);
			}
			// 更新计划任务
			record.schedule = now + debounce;
			record.invocation = invocation;
		}

		return null;
	}

	static class CallRecord {
		long schedule;
		long lastProceed = -1;
		MethodInvocation invocation;
	}

}
