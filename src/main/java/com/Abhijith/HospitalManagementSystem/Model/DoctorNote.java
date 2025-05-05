package com.Abhijith.HospitalManagementSystem.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DoctorNote {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String diagnosis;

private String instructions;

@OneToOne
@JoinColumn(name = "appointment_id", unique = true)
private Appointment appointment;

@OneToMany(mappedBy = "doctorNote", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Medication> medications = new ArrayList<>();
}
