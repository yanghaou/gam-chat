package com.chat.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class JMailUtil {
    @Autowired
    JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    String fromEmail;
    @Value("${spring.mail.nickname}")
    String nickName;

    public void sendMail1(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(nickName+"<"+fromEmail+">");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
//        MailUtil.send(to,subject,content,false);
    }
}
