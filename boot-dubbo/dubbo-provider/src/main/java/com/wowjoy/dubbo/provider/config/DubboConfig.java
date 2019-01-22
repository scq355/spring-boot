package com.wowjoy.dubbo.provider.config;

import com.alibaba.dubbo.config.*;
import com.wowjoy.dubbo.provider.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DubboConfig {
    @Bean
    public ApplicationConfig application() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("wowjoy-dubbo-provider");
        return application;
    }

    @Bean
    public RegistryConfig registry() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setId("registry-zookeeper");
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setClient("curator");
        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocol() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(12345);
        return protocolConfig;
    }

    @Bean
    public ServiceConfig<HelloService> helloServiceConfig(HelloService helloService) {
        ServiceConfig<HelloService> service = new ServiceConfig<>();
        service.setApplication(application());
        service.setRegistry(registry());
        service.setProtocol(protocol());
        service.setInterface(HelloService.class);
        service.setRef(helloService);
        service.setVersion("1.0.0");

        // 配置每一个method的信息，将method的设置关联到service配置中
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setName("hello");
        List<MethodConfig> methods = new ArrayList<>();
        methods.add(methodConfig);

        // 暴露及注册服务
        service.setMethods(methods);
        service.export();
        return service;
    }
}
