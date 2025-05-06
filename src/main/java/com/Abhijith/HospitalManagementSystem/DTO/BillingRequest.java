package com.Abhijith.HospitalManagementSystem.DTO;

import com.Abhijith.HospitalManagementSystem.Model.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class BillingRequest {

	@NotNull(message = "Amount is required")
	@Positive(message = "Amount must be positive")
	private Double amount;

	@NotNull(message = "Billing date is required")
	private LocalDateTime billingDate;

	@NotNull(message = "Appointment ID is required")
	private Long appointmentId;

	@NotNull(message = "Payment status is required")
	private PaymentStatus paymentStatus;
}
