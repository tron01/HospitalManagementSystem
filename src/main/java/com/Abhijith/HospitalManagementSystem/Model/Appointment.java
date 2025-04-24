package com.Abhijith.HospitalManagementSystem.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentTime;
    private String status; // SCHEDULED, COMPLETED, CANCELLED

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Doctor doctor;
}
