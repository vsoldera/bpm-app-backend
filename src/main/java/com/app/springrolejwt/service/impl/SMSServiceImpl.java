package com.akhianand.springrolejwt.service.impl;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.springframework.stereotype.Service;

@Service
public class SMSServiceImpl {
    public static final String ACCOUNT_SID = "AC78158ea3f2947a31df767af743539a25";
    public static final String AUTH_TOKEN = "5cb1a761c0c85bbe600f4814bd262c71";

    public Message sendSms() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+5519982266665"),
                new com.twilio.type.PhoneNumber("+13152104249"),
                "Oi, mamae! Eu te amo, se cuida!").create();

        return message;

    }
}
