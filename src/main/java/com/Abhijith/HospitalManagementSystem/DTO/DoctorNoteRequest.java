package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorNoteRequest {
private Long appointmentId;
private String diagnosis;
private String instructions;
private List<MedicationDTO> medications;
}

