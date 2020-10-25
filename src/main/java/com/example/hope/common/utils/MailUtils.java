package com.example.hope.common.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 邮箱工具类
 * @author: DHY
 * @created: 2020/10/24 23:48
 */
@Service
public class MailUtils {

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

    public void sendTokenMail(String to, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("touwaerioe@163.com");
        message.setTo(to);
        message.setSubject("重置密码密钥");
        message.setText(text);
        javaMailSender.send(message);
    }
}
