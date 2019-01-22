package com.wowjoy.boot.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootTaskApplicationTests {

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Test
    public void sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("测试通知");
        message.setText("这是内容");

        message.setTo("nwpuscq355@163.com");
        message.setFrom("1637517653@qq.com");
        javaMailSender.send(message);
    }

    @Test
    public void sendComplicatedMail() throws MessagingException {
        /**
         * 复杂邮件创建
         */
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setSubject("测试通知");
        messageHelper.setText("<b style='color:red'>这是内容</b>");
        messageHelper.setTo("nwpuscq355@163.com");
        messageHelper.setFrom("1637517653@qq.com");

        messageHelper.addAttachment("AsyncService.java", new File("D:/workspaces/boot-task/src/main/java/com/wowjoy/boot/task/service/AsyncService.java"));
        javaMailSender.send(mimeMessage);
    }

}
