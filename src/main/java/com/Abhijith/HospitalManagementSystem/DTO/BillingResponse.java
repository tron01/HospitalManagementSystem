package com.Abhijith.HospitalManagementSystem.DTO;

import com.Abhijith.HospitalManagementSystem.Model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class BillingResponse {
	private Long id;
	private Double amount;
	private LocalDateTime billingDate;
	private Long appointmentId;
	private String patientName;
	private String doctorName;
	private PaymentStatus paymentStatus;
}

