package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatientUpdateRequest {
	private String name;
	private Integer  age;
	private String gender;
	private String contact;
	private String address;
	private String username;
}
