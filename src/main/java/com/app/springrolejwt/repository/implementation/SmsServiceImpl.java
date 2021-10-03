package com.app.springrolejwt.repository.implementation;

import com.app.springrolejwt.config.WebSecurityConfig;
import com.app.springrolejwt.model.Health;
import com.app.springrolejwt.model.User;
import com.app.springrolejwt.repository.interfaces.HealthRepository;
import com.app.springrolejwt.repository.interfaces.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

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
    HealthRepository healthRepository;

    @Autowired
    PasswordEncoder encoder;

    @Transactional
    public Message sendSms(String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        log.info("User with phone number: " + phoneNumber);
        String token = generateToken();

        if(userRepository.existsByPhone(phoneNumber))  {
            Optional<User> user = userRepository.findByPhone(phoneNumber);

            user.get().setPassword(encoder.encode(token));
            user.get().setCode(token);

            userRepository.save(user.get());
        } else
            if(userRepository.existsByUsername(phoneNumber)) {
                Optional<User> user = userRepository.findByUsername(phoneNumber);
                user.get().setPassword(encoder.encode(token));
                user.get().setCode(token);
                userRepository.save(user.get());
            }
            else {
                User entity = new User();
                entity.setUsername(phoneNumber);
                entity.setPassword(encoder.encode(token));
                entity.setUuid("@" + UUID.randomUUID().toString().substring(0, 5).toUpperCase());
                Health health = new Health();
                health.setUuid(entity.getUuid());
                health.setUsername(entity.getUsername());
                entity.setCode(token);
                healthRepository.save(health);
                userRepository.save(entity);
            }


        return Message.creator(
                new com.twilio.type.PhoneNumber(phoneNumber),
                new com.twilio.type.PhoneNumber("+13152104249"),
                "Bem-vindo ao app BP. Seu código é: " + token).create();
    }

    public String generateToken() {
        return RandomStringUtils.random(6, true, true).toUpperCase();
    }
}