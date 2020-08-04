package cn.com.jcgroup.admin.interceptors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域拦截器
 * Created by wangl on 2017/5/8.
 */
@Component
public class CrossInterceptor extends HandlerInterceptorAdapter {

    @Value("${planb.access.control.domain}")
    private String planbAccessControlDomain;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", planbAccessControlDomain);
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.addHeader("Access-Control-Max-Age", "1800");
        response.addHeader("Access-Control-Allow-Headers","Content-Type,x-token");
        return super.preHandle(request, response, handler);
    }

}
