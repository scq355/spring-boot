package com.wowjoy.boot.amqp.service;

import com.wowjoy.boot.amqp.bean.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BookService {

    @RabbitListener(queues = "wowjoy.news")
    public void receive(Book book) {
        log.info("收到消息：{},", book);
    }

    @RabbitListener(queues = "wowjoy.emps")
    public void receiveWithHeader(Message message) {
        log.info("消息头：{}", message.getMessageProperties());
        log.info("消息内容：{}", message.toString());
    }
}
