package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class DoctorResponse {
    private Long id;
    private String username;
    private String name;
    private String specialization;
	private String contact;
	private String email;
}
