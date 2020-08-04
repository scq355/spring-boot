package com.wowjoy.boot.cache.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class TimeInterceptor extends HandlerInterceptorAdapter {
    private final NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<>("startTimeThreadLocal");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("拦截器preHandle开始*****");
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        log.info("handler 类：{}", handlerMethod.getBeanType().getName());
        log.info("handler 方法: {}", handlerMethod.getMethod().getName());

        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();

        for (MethodParameter methodParameter : methodParameters) {
            log.info("参数名称：{}", methodParameter.getParameterName());
        }
        startTimeThreadLocal.set(System.currentTimeMillis());
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long startTime = startTimeThreadLocal.get();
        long endTime = System.currentTimeMillis();

        log.info("拦截器消耗时间：{} ms ****", endTime - startTime);
        log.info("拦截器执行结束****");
    }
}
