package com.Abhijith.HospitalManagementSystem.DTO;

import com.Abhijith.HospitalManagementSystem.Model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class BillingRequest {
	private Double amount;
	private LocalDateTime billingDate;
	private Long appointmentId;
	private PaymentStatus paymentStatus;
}
