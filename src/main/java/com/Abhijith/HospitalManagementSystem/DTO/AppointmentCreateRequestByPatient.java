package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class AppointmentCreateRequestByPatient {
	private Long doctorId;
	private LocalDateTime appointmentDate;
	private String reason;
}
