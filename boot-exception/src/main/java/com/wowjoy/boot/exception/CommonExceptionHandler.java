package com.wowjoy.boot.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author scq
 * @date 2019/03/09 20:39:00
 */
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String,Object> exceptionHandler(Exception e){
        Map<String,Object> result = new HashMap<>(16);
        result.put("respCode", "9999");
        result.put("respMsg", e.getMessage());
//      正常开发中，可创建一个统一响应实体，如CommonResp
        return result;
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public Map<String, String> myErrorHandler(MyException ex) {
        Map<String, String> map = new HashMap<>();
        map.put("code", ex.getCode());
        map.put("msg", ex.getMsg());
        return map;
    }
}
