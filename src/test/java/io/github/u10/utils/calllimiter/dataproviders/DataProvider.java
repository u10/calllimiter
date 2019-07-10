package io.github.u10.utils.calllimiter.dataproviders;

import org.springframework.cache.annotation.Cacheable;

public interface DataProvider {
  @Cacheable(value = "one")
  String getData();
}
