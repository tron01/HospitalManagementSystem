package com.Abhijith.HospitalManagementSystem.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class DoctorUpdateRequest {

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Specialization is required")
	private String specialization;

	@NotBlank(message = "Contact is required")
	private String contact;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Username is required")
	private String username; // from Users table

}
