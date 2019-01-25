package cn.com.jcgroup.planb.interceptors;

import cn.com.jcgroup.planb.common.ApiResult;
import cn.com.jcgroup.planb.constant.PlanbIdentifier;
import cn.com.jcgroup.planb.constant.ResCodeEnum;
import cn.com.jcgroup.planb.util.ResponseUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *登录拦截器，此版本仅一台服务器，不做分布式session管理
 * @author LiuYong on 17/5/26 下午3:52.
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("[登录拦截器]请求url:{}",request.getRequestURI());
        HttpSession session = request.getSession(true);
        JSONObject user = (JSONObject)session.getAttribute(PlanbIdentifier.SESSION_USER);
        if(user == null){
            LOG.info("用户未登录");
            //返回错误
            ApiResult apiResult = new ApiResult(ResCodeEnum.SESSION_EXPIRED,"用户未登录");
            ResponseUtil.responseWithJson(response,apiResult);
            return  false;
        }
        return super.preHandle(request, response, handler);
    }
}
