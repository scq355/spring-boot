package com.wowjoy.boot.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackages = "com.wowjoy.boot.elasticsearch.repository")
@SpringBootApplication
public class BootElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootElasticsearchApplication.class, args);
    }
}
