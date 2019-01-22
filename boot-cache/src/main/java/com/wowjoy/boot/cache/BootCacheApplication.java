package com.wowjoy.boot.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.wowjoy.boot.cache.mapper")
@SpringBootApplication
public class BootCacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootCacheApplication.class, args);
    }
}
