package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponse {
	private Long id;
	private String patientName;
	private String doctorName;
	private LocalDateTime dateTime;
	private String reason;
	private String Status;
}
