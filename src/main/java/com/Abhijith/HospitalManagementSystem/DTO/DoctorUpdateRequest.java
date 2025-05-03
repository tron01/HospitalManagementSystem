package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DoctorUpdateRequest {
	private String name;
	private String specialization;
	private String contact;
	private String email;
	private String username; // from Users table
}
