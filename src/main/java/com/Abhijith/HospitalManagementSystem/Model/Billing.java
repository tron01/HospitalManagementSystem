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
public class Billing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private LocalDateTime billingDate;

    @OneToOne
    private Appointment appointment;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;// PAID, UNPAID
}
