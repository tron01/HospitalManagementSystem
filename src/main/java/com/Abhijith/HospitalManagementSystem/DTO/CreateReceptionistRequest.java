package com.Abhijith.HospitalManagementSystem.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateReceptionistRequest {
	@NotBlank(message = "Username is required")
	private String username;

	@NotBlank(message = "Password is required")
	private String password;

	@NotBlank(message = "Name is required")
	private String name;

	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Phone number is required")
	private String phone;
}
