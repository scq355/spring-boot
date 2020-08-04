package cn.com.jcgroup.admin.interceptors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 用来纪录方法调用时间的日志拦截器
 * @author LiuYong on 17/5/30 下午11:18.
 */
@Aspect
@Component
public class MethodTimeAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(MethodTimeAdvice.class);

    @Around(value = "within(cn.com.jcgroup.admin.rest..*)")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch clock = new StopWatch();
        clock.start(); // 计时开始
        Object result = joinPoint.proceed();
        clock.stop(); // 计时结束
        long time = clock.getTime();
//        LOG.info("[请求参数拦截]请求路径{},参数信息{}",);
        if(time >= 1000) {
            LOG.warn("[耗时拦截器]" + clock.getTime() + " ms ["
                    + joinPoint.getSignature().getDeclaringTypeName()  + "."
                    + joinPoint.getSignature().getName() + "("
                    + StringUtils.join(joinPoint.getArgs(), ",") + ")] ");
        }
        return result;
    }
}
