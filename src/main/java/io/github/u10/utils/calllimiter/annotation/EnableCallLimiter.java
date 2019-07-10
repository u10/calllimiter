package io.github.u10.utils.calllimiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.u10.utils.calllimiter.aop.CallLimitAdviser;
import io.github.u10.utils.calllimiter.aop.CallLimitInterceptor;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ CallLimitAdviser.class, CallLimitInterceptor.class })
public @interface EnableCallLimiter {
}
