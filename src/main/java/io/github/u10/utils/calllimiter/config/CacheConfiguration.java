package io.github.u10.utils.calllimiter.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfiguration {
  @Autowired
  private CallLimiterProperties callLimiterProperties;

  @Bean
  public CacheManager cacheManager(Ticker ticker) {
    List<CaffeineCache> caches = new ArrayList<>();
    for(Entry<String, CacheManagerProperties> entry : callLimiterProperties.getCacheManager().entrySet()) {
      CacheManagerProperties properties = entry.getValue();

      Caffeine<Object, Object> builder = Caffeine.newBuilder();
      Double expireAfterWrite = properties.getExpireAfterWrite();
      if (expireAfterWrite > 0) {
        expireAfterWrite *= 1000; // 转换成秒
        builder.expireAfterWrite(expireAfterWrite.longValue(), TimeUnit.MILLISECONDS);
      }

      Double expireAfterAccess = properties.getExpireAfterAccess();
      if (expireAfterAccess > 0) {
        expireAfterAccess *= 1000; // 转换成秒
        builder.expireAfterAccess(expireAfterAccess.longValue(), TimeUnit.MILLISECONDS);
      }

      builder
        .maximumSize(properties.getMaximumSize())
        .ticker(ticker);

      caches.add(new CaffeineCache(entry.getKey(), builder.build()));
    }
    SimpleCacheManager manager = new SimpleCacheManager();
    manager.setCaches(caches);
    return manager;
  }

  @Bean
  public Ticker ticker() {
      return Ticker.systemTicker();
  }
}
