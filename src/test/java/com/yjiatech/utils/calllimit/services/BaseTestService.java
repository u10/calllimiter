package com.yjiatech.utils.calllimit.services;

import com.yjiatech.utils.calllimit.annotation.CallLimit;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BaseTestService implements TestService {

  @CallLimit(debounce = 100, throttle = 1000)
  public void testHello (String x, long y) {
    log.info("Base Hello ++ {} {} {}", x, y, System.currentTimeMillis());
  }
}
