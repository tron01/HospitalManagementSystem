package com.Abhijith.HospitalManagementSystem.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private LocalDateTime billingDate;

    @OneToOne
    private Appointment appointment;

    private String paymentStatus; // PAID, UNPAID
}
