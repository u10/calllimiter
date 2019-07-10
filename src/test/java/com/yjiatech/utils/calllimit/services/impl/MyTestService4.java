package com.yjiatech.utils.calllimit.services.impl;

import com.yjiatech.utils.calllimit.annotation.CallLimit;
import com.yjiatech.utils.calllimit.services.BaseTestService;

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
