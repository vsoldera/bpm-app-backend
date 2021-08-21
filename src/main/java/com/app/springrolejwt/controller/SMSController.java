package com.app.springrolejwt.controller;

import com.app.springrolejwt.repository.implementation.SmsServiceImpl;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Log4j
public class SMSController {

    @Autowired
    private SmsServiceImpl smsService;

    @PostMapping("/sendSMS")
    public Message sendSMS(@RequestParam String phoneNumber) {
        log.info("There was a POST request to sign in using phone {}" + phoneNumber);
        return smsService.sendSms("+" + phoneNumber);
    }

}