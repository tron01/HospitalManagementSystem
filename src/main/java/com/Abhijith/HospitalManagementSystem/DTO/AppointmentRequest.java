package com.Abhijith.HospitalManagementSystem.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class AppointmentRequest {

	@NotNull(message = "Patient ID is required")
	private Long patientId;

	@NotNull(message = "Doctor ID is required")
	private Long doctorId;

	@NotNull(message = "Appointment date and time is required")
	@Future(message = "Appointment time must be in the future")
	private LocalDateTime dateTime;

	@NotBlank(message = "Reason is required")
	private String reason;
}

