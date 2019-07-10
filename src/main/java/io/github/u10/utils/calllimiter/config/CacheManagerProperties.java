package io.github.u10.utils.calllimiter.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CacheManagerProperties {
  private double expireAfterWrite = 1;
  private double expireAfterAccess = 0;
  private int maximumSize = 100;
}
