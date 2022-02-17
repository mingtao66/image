package com.yemei.image.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 日期切面 用于监听记录执行方法较长的方法
 */
@Component
@Aspect
public class LogAspect {

    @Pointcut("execution(public * com.yemei.controller..*.*(..)) or execution(public * com.yemei.util..*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Class declaringType = joinPoint.getSignature().getDeclaringType();
        try {
            return joinPoint.proceed();
        } finally {
            long time = System.currentTimeMillis() - start;
            if (time >= 200) {
                String name = joinPoint.getSignature().getName();
                LoggerUtil.error(declaringType, "Service method " + name + " used:" + time);
            }
        }


    }


}
