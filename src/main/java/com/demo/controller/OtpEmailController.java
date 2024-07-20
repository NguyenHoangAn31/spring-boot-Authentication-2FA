package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.OtpResponseDto;
import com.demo.dto.email.OtpEmailRequest;
import com.demo.dto.email.OtpEmailValidationRequest;
import com.demo.service.EmailService;

@RestController
@RequestMapping("/otp-email")
public class OtpEmailController {
    

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-otp")
	public OtpResponseDto sendOtp(@RequestBody OtpEmailRequest otpRequest) {
		return emailService.sendEmail(otpRequest);
	}
	@PostMapping("/validate-otp")
    public OtpResponseDto validateOtp(@RequestBody OtpEmailValidationRequest otpValidationRequest) {
        return emailService.validateOtp(otpValidationRequest);
    }
}
