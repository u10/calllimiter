package io.github.u10.utils.calllimiter.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import io.github.u10.utils.calllimiter.annotation.CallLimit;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;

@Component
public class CallLimitAdviser extends AbstractPointcutAdvisor {

  private static final long serialVersionUID = 1L;

	@Autowired
	private CallLimitInterceptor interceptor;

	private final StaticMethodMatcherPointcut pointcut = new StaticMethodMatcherPointcut() {
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
      CallLimit annotation = AnnotatedElementUtils.findMergedAnnotation(method, CallLimit.class);
      boolean match = annotation != null && !annotation.disabled();

      if (match) {
        if (Modifier.isPrivate(method.getModifiers())) throw new RuntimeException("CallLimit can not use on private method!");
        if ((method.getReturnType() != void.class)) throw new RuntimeException("CallLimit must use on void method!");
      }
      return match;
    }
	};

	@Override
  public Pointcut getPointcut() {
    return this.pointcut;
  }

  @Override
  public Advice getAdvice() {
    return this.interceptor;
  }

}
