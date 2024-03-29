package io.github.u10.utils.calllimiter.services;

import io.github.u10.utils.calllimiter.annotation.CallLimit;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BaseNoCallLimitTestService implements TestService {

  @CallLimit(disabled = true)
  public void testHello (String x, long y) {
    log.info("BaseNoCallLimit Hello ++ {} {} {}", x, y, System.currentTimeMillis());
  }
}
