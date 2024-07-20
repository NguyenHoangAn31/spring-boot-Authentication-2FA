package com.demo.dto.email;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpEmailValidationRequest {
    private String email;
	private String otpNumber;
}
