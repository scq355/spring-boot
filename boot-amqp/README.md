# boot-amqp
springboot整合mq

- RabbitAutoConfiguration：自动配置连接工厂

- RabbitProperties：封装了具体的配置项

- RabbitTemplate：给MQ发送/接收消息

- AmqpAdmin：RabbitMQ系统管理功能组件，创建和删除Queue, Exchange

- EnableRabbit + @RabbitListener：监听消息队列内容