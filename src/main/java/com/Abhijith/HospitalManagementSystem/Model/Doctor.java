package com.Abhijith.HospitalManagementSystem.Model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private String contact;
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
