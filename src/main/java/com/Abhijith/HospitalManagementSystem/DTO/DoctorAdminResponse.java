package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorAdminResponse {
	private Long id;
	private String name;
	private String username;
	private String contact;
	private String email;
	private String specialization;
	private boolean isEnabled;
	private boolean isAccountNonLocked;
}
