package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorRequest {
	private String name;
	private String specialization;
	private String contact;
	private String email;
}

