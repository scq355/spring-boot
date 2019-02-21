package com.wowjoy.boot.handler;

import com.alibaba.fastjson.JSON;
import com.wowjoy.boot.common.RException;
import com.wowjoy.boot.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author scq
 */
@Slf4j
@Component
public class RExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object o,
                                         Exception e) {

        Result result = new Result();

        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        if (e instanceof RException) {
            result.put("code", ((RException) e).getCode());
            result.put("msg", e.getMessage());
        } else if(e instanceof DuplicateKeyException){
            result = Result.error("数据库中已存在该记录");
        } else {
            result = Result.error();
        }

        log.error(e.getMessage(), e);

        String json = JSON.toJSONString(result);
        try {
            response.getWriter().print(json);
        } catch (IOException e1) {
            log.error("RRExceptionHandler 异常处理失败", e);
        }
        return new ModelAndView();
    }
}
