package com.akhianand.springrolejwt;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SpringRoleJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRoleJwtApplication.class, args);
	}

}
