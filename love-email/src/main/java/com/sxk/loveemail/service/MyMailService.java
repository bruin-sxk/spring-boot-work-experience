package com.sxk.loveemail.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Data
public class MyMailService {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${she.mail}")
    private String[] sheMail;

    public void sendMessage(String subject, String message) {

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            //发送者邮件邮箱
            helper.setFrom(from);
            //收邮件者邮箱
            helper.setTo(sheMail);
            //发件主题
            helper.setSubject(subject);
            //发件内容
            helper.setText(message);
            //发送邮件
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 远程获取要发送的信息
     */
    public String getOneS() {
        final ResponseEntity<String> resultString = restTemplate.getForEntity("https://chp.shadiao.app/api.php", String.class);
        return resultString.getBody();
    }


}
