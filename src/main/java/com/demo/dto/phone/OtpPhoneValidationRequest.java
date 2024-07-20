package com.demo.dto.phone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpPhoneValidationRequest {
	private String phoneNumber;
	private String otpNumber;
}
