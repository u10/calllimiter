package io.github.u10.utils.calllimiter.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LimitManagerProperties {
  private int cacheSize = 1024;
  private int poolSize = 0;
}
