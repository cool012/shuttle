package com.example.hope.service.other;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 邮箱工具类
 * @author: DHY
 * @created: 2020/10/24 23:48
 */

@Service
public class MailService {

    @Resource
    private JavaMailSender javaMailSender;

    @Async("taskExecutor")
    public void sendTokenMail(String to, String text, String subject) {
        try {
            Thread.sleep(1000);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("touwaerioe@163.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
