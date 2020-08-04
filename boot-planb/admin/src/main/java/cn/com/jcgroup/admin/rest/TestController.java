package cn.com.jcgroup.admin.rest;

import cn.com.jcgroup.admin.common.ApiResult;
import cn.com.jcgroup.admin.dto.TestDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基金同步临时测试类
 * @author LiuYong on 17/6/16 下午12:05.
 */
//TODO  移除代码
@RestController
@Validated
public class TestController {
        
    @RequestMapping("/test")
    public ApiResult test(@RequestBody TestDto testDto){
        
        return new ApiResult("hello ");
    }
}
