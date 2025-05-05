package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceptionistUpdateRequest {
	private String username;
	private String name;
	private String phone;
	private String email;
}
