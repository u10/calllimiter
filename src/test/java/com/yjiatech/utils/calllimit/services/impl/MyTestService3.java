package com.yjiatech.utils.calllimit.services.impl;

import com.yjiatech.utils.calllimit.services.BaseTestService;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyTestService3 extends BaseTestService {

  @Override
  // Use base call limit config
  public void testHello (String x, long y) {
    log.info("My Hello3 ++ {} {} {}", x, y, System.currentTimeMillis());
  }

}
