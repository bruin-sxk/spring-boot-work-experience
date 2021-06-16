package com.sxk.loveemail.schedu;

import com.sxk.loveemail.constant.MailConstant;
import com.sxk.loveemail.service.MyMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduled {

    @Autowired
    private MyMailService sendMessage;

    /**
     * 定时执行任务方法 每天5点20执行该任务
     */
    @Scheduled(cron = "0 20 17 * * *")
    public void dsrw() {
        String message = sendMessage.getOneS();
        sendMessage.sendMessage(MailConstant.SUBJECT, message);
    }

}
