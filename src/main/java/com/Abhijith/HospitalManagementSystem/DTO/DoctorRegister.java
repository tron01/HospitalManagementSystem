package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DoctorRegister {
    private String username;
    private String password;
    private String name;
    private String specialization;
    private String contact;
    private String email;
}
