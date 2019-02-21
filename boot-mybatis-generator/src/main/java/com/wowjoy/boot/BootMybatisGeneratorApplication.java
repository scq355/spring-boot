package com.wowjoy.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author scq
 */
@SpringBootApplication
@MapperScan(value = "com.wowjoy.boot")
public class BootMybatisGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMybatisGeneratorApplication.class, args);
    }

}
