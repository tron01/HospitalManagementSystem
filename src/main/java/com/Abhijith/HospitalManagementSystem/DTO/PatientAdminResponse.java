package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientAdminResponse {
	private Long id;
	private String name;
	private String username;
	private int age;
	private String gender;
	private String contact;
	private String address;
	private boolean isEnabled;
	private boolean isAccountNonLocked;
}
