package com.Abhijith.HospitalManagementSystem.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceptionistUpdateRequest {

	@NotBlank(message = "Username is required")
	private String username;

	@NotBlank(message = "Name is required")
	private String name;

	@NotBlank(message = "Phone is required")
	private String phone;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;
}
