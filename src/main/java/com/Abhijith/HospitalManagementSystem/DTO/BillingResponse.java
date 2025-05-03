package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class BillingResponse {
	private Long id;
	private String patientName;
	private String doctorName;
	private LocalDateTime appointmentDate;
	private Double amount;
	private LocalDateTime billingDate;
	private String paymentStatus;
}

