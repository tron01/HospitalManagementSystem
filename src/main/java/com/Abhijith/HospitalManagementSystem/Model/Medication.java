package com.Abhijith.HospitalManagementSystem.Model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Medication {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String name;
private String dosage;         // e.g., "500mg"
private String frequency;      // e.g., "Twice a day"
private String timing;         // e.g., "After food"

@ManyToOne
@JoinColumn(name = "doctor_note_id")
private DoctorNote doctorNote;
}
