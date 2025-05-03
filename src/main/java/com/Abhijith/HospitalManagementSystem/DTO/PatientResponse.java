package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatientResponse {
    private Long id;
    private String username;
    private String name;
    private String address;
    private int age;
    private String gender;
    private String contact;
}
