package com.wowjoy.boot.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author scq
 */
@SpringBootApplication
public class BootMongodbApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootMongodbApplication.class, args);
    }

}

