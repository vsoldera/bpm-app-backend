package com.akhianand.springrolejwt.controller;

import com.akhianand.springrolejwt.service.impl.SMSServiceImpl;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SMSController {

    @Autowired
    private SMSServiceImpl smsService;
    
    @PostMapping("/sendSMS")
    public Message sendSMS() {
        return smsService.sendSms();
    }

}
