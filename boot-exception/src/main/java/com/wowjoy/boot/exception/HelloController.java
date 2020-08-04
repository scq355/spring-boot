package com.wowjoy.boot.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author scq
 * @date 2019/03/09 20:51:00
 */
@RestController
public class HelloController {

    @RequestMapping("/home")
    public String home() {

        throw new StackOverflowError("内存溢出");
//        throw new IllegalArgumentException("参数错误");
//        throw new NullPointerException();
//        throw new Exception("Sam 错误");
//        throw new MyException("101", "Sam 错误");

    }
}
