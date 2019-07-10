package io.github.u10.utils.calllimiter.dataproviders.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import io.github.u10.utils.calllimiter.dataproviders.DataProvider;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyDataProvider1 implements DataProvider {

  @Cacheable(value = "two")
  public String getData () {
    log.info("call getData1");
    return System.currentTimeMillis() + "";
  }

}
