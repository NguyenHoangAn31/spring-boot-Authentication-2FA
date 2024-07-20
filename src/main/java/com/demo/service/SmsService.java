package com.demo.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.config.AppConfig;
import com.demo.dto.OtpResponseDto;
import com.demo.dto.OtpStatus;
import com.demo.dto.phone.OtpPhoneRequest;
import com.demo.dto.phone.OtpPhoneValidationRequest;
import com.demo.repository.UserRepository;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class SmsService {

	@Autowired
	private AppConfig twilioConfig;

	@Autowired
	private UserRepository userRepository;

	Map<String, String> otpMap = new HashMap<>();

	public OtpResponseDto sendSMS(OtpPhoneRequest otpRequest) {
		OtpResponseDto otpResponseDto = null;
		try {
			int id = userRepository.findIdByPhoneRegister(otpRequest.getPhoneNumber()).orElse(0);
			if (id == 0) {
				otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, "Phone number is not registered");
				return otpResponseDto;
			}

			PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());// to
			PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
			String otp = generateOTP();
			String otpMessage = "Dear Customer , Your OTP is  " + otp
					+ " for sending sms through Spring boot application. Thank You.";

			// send otp
			Message message = Message.creator(to, from, otpMessage).create();
			otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);

			userRepository.refreshOtp(id, otp);

		} catch (Exception e) {
			e.printStackTrace();
			otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
		}
		return otpResponseDto;
	}

	public OtpResponseDto validateOtp(OtpPhoneValidationRequest otpValidationRequest) {
		boolean result = userRepository.validateOtpPhone(otpValidationRequest.getPhoneNumber(),
				otpValidationRequest.getOtpNumber());
		OtpResponseDto otpResponseDto = null;
		otpResponseDto = result ? new OtpResponseDto(OtpStatus.DELIVERED, "Valid Otp")
				: new OtpResponseDto(OtpStatus.FAILED, "Invalid Otp");
		return otpResponseDto;
	}

	private String generateOTP() {
		return new DecimalFormat("000000")
				.format(new Random().nextInt(999999));
	}

}
