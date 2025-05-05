package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceptionistAdminResponse {
	private Long id;
	private String name;
	private String phone;
	private String email;
	private String username;
}
