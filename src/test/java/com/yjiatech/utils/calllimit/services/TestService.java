package com.yjiatech.utils.calllimit.services;

import com.yjiatech.utils.calllimit.annotation.CallLimit;
import org.springframework.stereotype.Component;

@Component
public interface TestService {

  @CallLimit(debounce = 200, throttle = 2000)
  void testHello (String x, long y);
}
