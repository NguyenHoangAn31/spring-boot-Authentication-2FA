package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.demo.config.AppConfig;
import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Authentication2FAApplication {


	@Autowired
	private AppConfig appConfig;

	@PostConstruct
	public void setup() {
		Twilio.init(appConfig.getAccountSid(), appConfig.getAuthToken());
	}

	public static void main(String[] args) {
		SpringApplication.run(Authentication2FAApplication.class, args);
	}

}
