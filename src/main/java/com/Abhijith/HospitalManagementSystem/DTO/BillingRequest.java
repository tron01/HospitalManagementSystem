package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BillingRequest {
	private Long appointmentId;
	private Double amount;
	private String paymentStatus;
}
