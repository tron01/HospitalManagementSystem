package com.Abhijith.HospitalManagementSystem.DTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class AppointmentCreateRequestByPatient {

	@NotNull(message = "Doctor ID is required")
	private Long doctorId;

	@NotNull(message = "Appointment date is required")
	@Future(message = "Appointment date must be in the future")
	private LocalDateTime appointmentDate;

	@NotBlank(message = "Reason is required")
	private String reason;

}
