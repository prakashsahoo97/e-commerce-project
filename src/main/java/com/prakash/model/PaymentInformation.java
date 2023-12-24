package com.prakash.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInformation {

	@Column(name="cardholder_name")
	private String cardholderName;
	
	private String cardNumber;
	
	private LocalDateTime expirationDate;
	
	private String cvv;
}
