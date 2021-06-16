package com.sxk.loveemail.controller;

import com.sxk.loveemail.constant.MailConstant;
import com.sxk.loveemail.service.MyMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/love")
public class SendController {

    @Autowired
    private MyMailService mailService;

    @GetMapping
    public String loveMail() {
        final String message = mailService.getOneS();
        mailService.sendMessage(MailConstant.SUBJECT, message);
        return message;
    }

}
