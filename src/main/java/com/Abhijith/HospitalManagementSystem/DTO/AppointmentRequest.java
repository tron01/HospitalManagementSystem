package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class AppointmentRequest {
	private Long patientId;
	private Long doctorId;
	private LocalDateTime dateTime;
	private String reason;
}

