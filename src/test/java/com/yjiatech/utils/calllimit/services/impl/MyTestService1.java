package com.yjiatech.utils.calllimit.services.impl;

import com.yjiatech.utils.calllimit.services.TestService;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyTestService1 implements TestService {

  @Override
  // Use interface call limit config
  public void testHello (String x, long y) {
    log.info("My Hello1 ++ {} {} {}", x, y, System.currentTimeMillis());
  }

}
