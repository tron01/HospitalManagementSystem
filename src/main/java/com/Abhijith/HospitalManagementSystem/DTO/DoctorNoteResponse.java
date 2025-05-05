package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class DoctorNoteResponse {
	private Long id;
	private String diagnosis;
	private String instructions;
	private Long appointmentId;
	private List<MedicationDTO> medications;
}
