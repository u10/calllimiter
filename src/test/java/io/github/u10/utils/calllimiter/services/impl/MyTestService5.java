package io.github.u10.utils.calllimiter.services.impl;

import io.github.u10.utils.calllimiter.services.BaseNoCallLimitTestService;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyTestService5 extends BaseNoCallLimitTestService {

  @Override
  // Use base disable call limit
  public void testHello (String x, long y) {
    log.info("My Hello5 ++ {} {} {}", x, y, System.currentTimeMillis());
  }

}
