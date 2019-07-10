package com.yjiatech.utils.calllimit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yjiatech.utils.calllimit.aop.CallLimitAdviser;
import com.yjiatech.utils.calllimit.aop.CallLimitInterceptor;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ CallLimitAdviser.class, CallLimitInterceptor.class })
public @interface EnableCallLimiter {
}
