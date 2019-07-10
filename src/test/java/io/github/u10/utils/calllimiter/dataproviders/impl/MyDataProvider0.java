package io.github.u10.utils.calllimiter.dataproviders.impl;

import org.springframework.stereotype.Component;

import io.github.u10.utils.calllimiter.dataproviders.BaseDataProvider;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyDataProvider0 extends BaseDataProvider {

  @Override
  public String getData () {
    log.info("call getData0");
    return System.currentTimeMillis() + "";
  }
}
