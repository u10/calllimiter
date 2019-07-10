package com.yjiatech.utils.calllimit.services.impl;

import com.yjiatech.utils.calllimit.annotation.CallLimit;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyTestService0 {

  // Use call limit config
  @CallLimit(debounce = 500, throttle = 5000)
  public void testHello (String x, long y) {
    log.info("My Hello ++ {} {} {}", x, y, System.currentTimeMillis());
  }

}
