package io.github.u10.utils.calllimiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CallLimit {
	boolean disabled() default false;
	long debounce() default 1000;
	long throttle() default 1000;
	String id() default "";
	String key() default "";
}
