package com.demo.service;

import java.text.DecimalFormat;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.demo.dto.OtpResponseDto;
import com.demo.dto.OtpStatus;
import com.demo.dto.email.OtpEmailRequest;
import com.demo.dto.email.OtpEmailValidationRequest;
import com.demo.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    public OtpResponseDto sendEmail(OtpEmailRequest otpRequest) {

        OtpResponseDto otpResponseDto = null;
        try {
            int id = userRepository.findIdByEmailRegister(otpRequest.getEmail()).orElse(0);
            if (id == 0) {
                otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, "Email is not registered");
                return otpResponseDto;
            }
            String otp = generateOTP();

            String otpMessage = "Dear Customer , Your OTP is  " + otp
                    + " for sending sms through Spring boot application. Thank You.";

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(otpRequest.getEmail());
            mimeMessageHelper.setSubject("Verify OTP");
            mimeMessageHelper.setText(otpMessage);
            javaMailSender.send(mimeMessage);

            otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);
            userRepository.refreshOtp(id, otp);

        } catch (Exception e) {
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return otpResponseDto;

    }

    public OtpResponseDto validateOtp(OtpEmailValidationRequest otpValidationRequest) {
		boolean result = userRepository.validateOtpEmai(otpValidationRequest.getEmail(),
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
