package com.app.springrolejwt.repository.implementation;

import com.app.springrolejwt.config.WebSecurityConfig;
import com.app.springrolejwt.model.User;
import com.app.springrolejwt.repository.interfaces.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SmsServiceImpl {
    public static final String ACCOUNT_SID = "AC78158ea3f2947a31df767af743539a25";
    public static final String AUTH_TOKEN = "5cb1a761c0c85bbe600f4814bd262c71";


    AuthenticationManager authenticationManager;
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    public Message sendSms(String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        log.info("User with phone number: " + phoneNumber);
        String token = generateToken();

        if(userRepository.existsByPhone(phoneNumber))  {
            User user = userRepository.findByPhone(phoneNumber);
            user.setPassword(encoder.encode(token));
            user.setCode(token);
            userRepository.save(user);
        } else
            if(userRepository.existsByUsername(phoneNumber)) {
                User user = userRepository.findByUsername(phoneNumber);
                user.setPassword(encoder.encode(token));
                user.setCode(token);
                userRepository.save(user);
            }
            else {
            User entity = new User();
            entity.setUsername(phoneNumber);
            entity.setPassword(encoder.encode(token));
            entity.setCode(token);
            userRepository.save(entity);
        }


        return Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                new com.twilio.type.PhoneNumber("+13152104249"),
                "Bem-vindo ao app BPM. Seu código é: " + token).create();
    }

    public String generateToken() {
        return RandomStringUtils.random(6, true, true).toUpperCase();
    }
}