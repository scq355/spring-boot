package cn.com.jcgroup.planb.interceptors;

import cn.com.jcgroup.planb.annotations.ProjectAuth;
import cn.com.jcgroup.service.service.SecurityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 项目相关菜单权限校验
 *
 * @author LiuYong on 17/5/30 下午6:58.
 */
@Component
public class ProjectAuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectAuthInterceptor.class);
    @Autowired
    private SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            LOG.info("[项目菜单拦截器]请求url:{}", request.getRequestURI());
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> clazz = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            if(clazz != null && method!=null){
                boolean isProjectAuthAnnotation = clazz.isAnnotationPresent(ProjectAuth.class);
                if(isProjectAuthAnnotation){
                    ProjectAuth projectAuth = method.getAnnotation(ProjectAuth.class);
                    String menu = projectAuth.menu();
                   //校验用户是否有权限访问改项目


                    //校验用户是否有权限访问menu
                    if(StringUtils.isEmpty(menu)){
                        //若menu为空，则只校验用户是否有访问项目的权限

                    }else{

                    }
                }
            }
        }
        return super.preHandle(request, response, handler);
    }
}
