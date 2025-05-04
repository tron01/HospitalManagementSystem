package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateReceptionistRequest {
	private String username;
	private String password;
	private String name;
	private String email;
	private String phone;
}
