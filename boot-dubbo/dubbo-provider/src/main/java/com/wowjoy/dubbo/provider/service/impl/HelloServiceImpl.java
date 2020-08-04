package com.wowjoy.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wowjoy.dubbo.provider.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello " + name + ", from dubbo provider.";
    }
}
