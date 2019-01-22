package com.wowjoy.boot.amqp.config;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 开启基于注解的RabbitMQ
@EnableRabbit
@Configuration
public class AmqpConfig {

   @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
   }

}
