package com.Abhijith.HospitalManagementSystem.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatientUpdateRequest {

	@NotBlank(message = "Name is required")
	private String name;

	@NotNull(message = "Age is required")
	@Min(value = 0, message = "Age must be non-negative")
	private Integer age;

	@NotBlank(message = "Gender is required")
	private String gender;

	@NotBlank(message = "Contact is required")
	private String contact;

	@NotBlank(message = "Address is required")
	private String address;

	@NotBlank(message = "Username is required")
	private String username;

}
