package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.OtpResponseDto;
import com.demo.dto.phone.OtpPhoneRequest;
import com.demo.dto.phone.OtpPhoneValidationRequest;
import com.demo.service.SmsService;

@RestController
@RequestMapping("/otp-phone")
public class OtpPhoneController {

	@Autowired
	private SmsService smsService;


	@PostMapping("/send-otp")
	public OtpResponseDto sendOtp(@RequestBody OtpPhoneRequest otpRequest) {
		
		return smsService.sendSMS(otpRequest);
	}
	@PostMapping("/validate-otp")
    public OtpResponseDto validateOtp(@RequestBody OtpPhoneValidationRequest otpValidationRequest) {
		return smsService.validateOtp(otpValidationRequest);
    }
	
}
