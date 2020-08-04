package com.wowjoy.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wowjoy.dubbo.provider.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Reference
    private HelloService helloService;

    @GetMapping(value = "/hello/{name}")
    public String hello(@PathVariable String name) {
        return helloService.hello(name);
    }

}
