package com.app.springrolejwt.repository.implementation;

import com.app.springrolejwt.repository.interfaces.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SmsServiceImpl {
    public static final String ACCOUNT_SID = "AC78158ea3f2947a31df767af743539a25";
    public static final String AUTH_TOKEN = "5cb1a761c0c85bbe600f4814bd262c71";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    public Message sendSms(String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        log.info("User phone number is: " + phoneNumber);

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                new com.twilio.type.PhoneNumber("+13152104249"),
                "Bem-vindo ao BPM. Seu código é: " + generateToken()).create();

        return message;

    }

    public String generateToken() {
        return RandomStringUtils.random(6, true, true).toUpperCase().toString();
    }
}