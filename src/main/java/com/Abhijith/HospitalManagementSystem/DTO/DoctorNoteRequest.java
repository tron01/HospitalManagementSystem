package com.Abhijith.HospitalManagementSystem.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorNoteRequest {

	@NotNull(message = "Appointment ID is required")
	private Long appointmentId;

	@NotBlank(message = "Diagnosis is required")
	private String diagnosis;

	@NotBlank(message = "Instructions are required")
	private String instructions;

	@NotNull(message = "Medications list is required")
	@Size(min = 1, message = "At least one medication is required")
	@Valid
	private List<MedicationDTO> medications;
}

