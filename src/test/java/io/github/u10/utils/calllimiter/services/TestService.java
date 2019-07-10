package io.github.u10.utils.calllimiter.services;

import io.github.u10.utils.calllimiter.annotation.CallLimit;
import org.springframework.stereotype.Component;

@Component
public interface TestService {

  @CallLimit(debounce = 200, throttle = 2000)
  void testHello (String x, long y);
}
