package io.github.u10.utils.calllimiter;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import io.github.u10.utils.calllimiter.annotation.EnableCallLimiter;
import io.github.u10.utils.calllimiter.annotation.EnableCallLimiterCacheManager;
import io.github.u10.utils.calllimiter.dataproviders.impl.MyDataProvider0;
import io.github.u10.utils.calllimiter.services.TestService;
import io.github.u10.utils.calllimiter.services.impl.MyTestService0;
import io.github.u10.utils.calllimiter.services.impl.MyTestService1;
import io.github.u10.utils.calllimiter.services.impl.MyTestService2;
import io.github.u10.utils.calllimiter.services.impl.MyTestService3;
import io.github.u10.utils.calllimiter.services.impl.MyTestService4;
import io.github.u10.utils.calllimiter.services.impl.MyTestService5;
import io.github.u10.utils.calllimiter.services.impl.MyTestService6;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCallLimiter
@EnableCallLimiterCacheManager
@Slf4j
public class CallLimitTests {
	private ApplicationContext context;

	@Before
	public void init () {
		context = SpringApplication.run(CallLimitTests.class);
		log.info("Prepare context...");
	}

	@Test
	public void baseLimitTest() throws InterruptedException {
		MyTestService0 service = context.getBean(MyTestService0.class);

		Arrays.asList(new String[500]).stream().parallel().forEach((item) -> {
			service.testHello("x", 10);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void paramsLimitTest() throws InterruptedException {
		MyTestService0 service = context.getBean(MyTestService0.class);

		Arrays.asList(new String[100]).stream().parallel().forEach((item) -> {
			service.testHello("x", System.currentTimeMillis() % 3);
			try {
				Thread.sleep(100 + Double.valueOf(Math.random() * 1000).longValue());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void testLimit() {
		testService(MyTestService1.class);
		testService(MyTestService2.class);
		testService(MyTestService3.class);
		testService(MyTestService4.class);
		testService(MyTestService5.class);
		testService(MyTestService6.class);
	}

	@Test
	public void testCache() {
		MyDataProvider0 provider = context.getBean(MyDataProvider0.class);

		Arrays.asList(new String[100]).stream().parallel().forEach((item) -> {
			log.info("get data: {}", provider.getData());
			try {
				Thread.sleep(100 + Double.valueOf(Math.random() * 1000).longValue());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	private void testService(Class<? extends TestService> clazz) {
		testService(clazz, 500, 10);
	}

	private void testService(Class<? extends TestService> clazz, int nCount, int nParallel) {
		TestService service = (TestService) context.getBean(clazz);

		ForkJoinPool pool = new ForkJoinPool(nParallel);
		pool.submit(() -> {
			Arrays.asList(new String[nCount]).stream().parallel().forEach((item) -> {
				service.testHello("string", 10);
				try {
					Thread.sleep(Double.valueOf(Math.random() * 100).longValue());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}).join();

		pool.shutdown();

	}

}
