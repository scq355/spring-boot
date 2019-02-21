package com.wowjoy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wowjoy.dao")
public class MyBatisGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBatisGeneratorApplication.class, args);
	}
}
