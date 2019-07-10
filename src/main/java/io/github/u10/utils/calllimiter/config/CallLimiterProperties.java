package io.github.u10.utils.calllimiter.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "u10.call-limiter")
public class CallLimiterProperties {
  private Map<String, CacheManagerProperties> cacheManager = new HashMap<>();

  private LimitManagerProperties limitManager = new LimitManagerProperties();
}
