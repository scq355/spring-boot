package com.wowjoy.boot.amqp;

import com.wowjoy.boot.amqp.bean.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.amqp.core.Binding.DestinationType.QUEUE;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BootAmqpApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 单播(点对点消息)
     */
    @Test
    public void testSingle() {

        /**
         * rabbitTemplate.send(String exchange, String routingKey, Message message);
         * message需要自己定义（消息头和消息内容）
         */

        /**
         * rabbitTemplate.convertAndSend(String exchange, String routingKey, Object message);
         * 只传发送的对象，可以自动序列化发送给rabbitMQ
         */

        // 消息内容
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "第一条消息");
        map.put("note", Arrays.asList("name", "中國", 123));
        map.put("book", new Book("Java从入门到出家", "百晓生"));
        map.put("date", new Date());
        // 发送消息
        rabbitTemplate.convertAndSend("exchange.direct", "wowjoy.news", new Book("Java从入门到出家", "百晓生"));
    }

    // 接收数据
    @Test
    public void receive() {
        Object message = rabbitTemplate.receiveAndConvert("wowjoy.news");
        log.info("MessageType:{}", message.getClass());
        log.info("Message:{}", message);
    }

    /**
     * 广播
     */
    @Test
    public void testAll() {
        rabbitTemplate.convertAndSend("exchange.fanout", "", new Book("MySQL从入门到删库跑路", "百晓生"));
    }


    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void testExchange() {
        amqpAdmin.declareExchange(new DirectExchange("amqpAdmin.exchange"));
        log.info("创建Exchange完成");
    }

    @Test
    public void testQueue() {
        amqpAdmin.declareQueue(new Queue("amqpAdmin.queue", true));
    }

    @Test
    public void testBind() {
        amqpAdmin.declareBinding(new Binding("amqpAdmin.queue",
                QUEUE,
                "amqpAdmin.exchange",
                "t-queue",
                null));
    }

}
