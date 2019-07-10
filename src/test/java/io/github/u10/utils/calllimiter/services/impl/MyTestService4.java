package io.github.u10.utils.calllimiter.services.impl;

import io.github.u10.utils.calllimiter.annotation.CallLimit;
import io.github.u10.utils.calllimiter.services.BaseTestService;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyTestService4 extends BaseTestService {

  @Override
  // Use current call limit config
  @CallLimit(debounce = 50, throttle = 200)
  public void testHello (String x, long y) {
    log.info("My Hello4 ++ {} {} {}", x, y, System.currentTimeMillis());
  }

}
