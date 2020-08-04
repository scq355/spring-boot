package com.wowjoy.boot.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 开启定时器
@EnableAsync // 开启异步注解
@SpringBootApplication
public class BootTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootTaskApplication.class, args);
    }
}
