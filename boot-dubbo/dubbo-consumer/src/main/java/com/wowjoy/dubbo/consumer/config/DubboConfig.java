package com.wowjoy.dubbo.consumer.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.wowjoy.dubbo.provider.service.HelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableDubbo
@Configuration
public class DubboConfig {

    @Bean
    ApplicationConfig application() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("wowjoy-dubbo-consumer");
        return application;
    }

    @Bean
    public RegistryConfig registry() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setClient("curator");
        return registryConfig;
    }

    @Bean
    public ConsumerConfig consumer() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setTimeout(3000);
        return consumerConfig;
    }

    @Bean
    ReferenceConfig reference() {
        ReferenceConfig<HelloService> reference = new ReferenceConfig<>();
        reference.setApplication(application());
        reference.setConsumer(consumer());
        reference.setRegistry(registry());
        reference.setInterface(HelloService.class);
        reference.setVersion("1.0.0");

        return reference;
    }
}
