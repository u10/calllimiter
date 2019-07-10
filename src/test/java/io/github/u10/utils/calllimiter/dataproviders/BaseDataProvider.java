package io.github.u10.utils.calllimiter.dataproviders;

import org.springframework.cache.annotation.Cacheable;

public abstract class BaseDataProvider {
  @Cacheable(value = "one")
  public abstract String getData();
}
