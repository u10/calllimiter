package com.yjiatech.utils.calllimit;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import com.yjiatech.utils.calllimit.annotation.EnableCallLimiter;
import com.yjiatech.utils.calllimit.services.TestService;
import com.yjiatech.utils.calllimit.services.impl.MyTestService0;
import com.yjiatech.utils.calllimit.services.impl.MyTestService1;
import com.yjiatech.utils.calllimit.services.impl.MyTestService2;
import com.yjiatech.utils.calllimit.services.impl.MyTestService3;
import com.yjiatech.utils.calllimit.services.impl.MyTestService4;
import com.yjiatech.utils.calllimit.services.impl.MyTestService5;
import com.yjiatech.utils.calllimit.services.impl.MyTestService6;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCallLimiter
@Slf4j
public class CallLimitTests {
	private ApplicationContext context;

	@Before
	public void init () {
		context = SpringApplication.run(CallLimitTests.class);
		log.info("Prepare context...");
	}

	@Test
	public void baseTest() throws InterruptedException {
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
	public void paramsTest() throws InterruptedException {
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
	public void test() {
		testService(MyTestService1.class);
		testService(MyTestService2.class);
		testService(MyTestService3.class);
		testService(MyTestService4.class);
		testService(MyTestService5.class);
		testService(MyTestService6.class);
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
