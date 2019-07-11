package io.github.u10.utils.calllimiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.u10.utils.calllimiter.config.CacheConfiguration;
import io.github.u10.utils.calllimiter.config.CallLimiterProperties;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableCaching
@Import({ CallLimiterProperties.class, CacheConfiguration.class })
public @interface EnableCallLimiterCacheManager {
}
