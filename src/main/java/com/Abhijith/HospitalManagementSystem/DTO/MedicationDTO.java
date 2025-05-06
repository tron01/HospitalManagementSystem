package com.Abhijith.HospitalManagementSystem.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class MedicationDTO {

	@NotBlank(message = "Medication name is required")
	private String name;

	@NotBlank(message = "Dosage is required")
	private String dosage;

	@NotBlank(message = "Frequency is required")
	private String frequency;

	@NotBlank(message = "Timing is required")
	private String timing;
}
