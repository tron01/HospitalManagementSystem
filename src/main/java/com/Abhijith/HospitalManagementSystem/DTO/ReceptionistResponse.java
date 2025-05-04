package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReceptionistResponse {
	private Long id;
	private String name;
	private String email;
	private String phone;
	private String username;
}