package com.Abhijith.HospitalManagementSystem.Controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
	private Long id;
	private String username;
	private String role;
	private boolean isEnabled;
	private boolean isAccountNonLocked;
}
