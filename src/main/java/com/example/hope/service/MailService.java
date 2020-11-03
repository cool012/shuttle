package com.example.hope.service;

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

    public void sendTextMail(String subject, String from, String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    @Async("taskExecutor")
    public void sendTokenMail(String to, String text) {
        try {
            Thread.sleep(1000);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("touwaerioe@163.com");
            message.setTo(to);
            message.setSubject("重置密码密钥");
            message.setText(text);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
