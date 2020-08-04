package com.wowjoy.boot.cache.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeAspect {

    @Pointcut(value = "within(com.wowjoy.boot.cache.controller..*)")
    public void timePointCut() {}

    @Around("timePointCut()")
    public Object timeProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("切面环绕开始====");
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("参数为 {} ", arg);
        }

        long startTime = System.currentTimeMillis();

        Object object = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("切面执行时间：{} ms", endTime - startTime);
        log.info("切面执行结束====");
        return object;
    }
}
