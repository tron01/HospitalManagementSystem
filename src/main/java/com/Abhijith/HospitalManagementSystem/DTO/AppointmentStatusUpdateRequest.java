package com.Abhijith.HospitalManagementSystem.DTO;

import com.Abhijith.HospitalManagementSystem.Model.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AppointmentStatusUpdateRequest {
	private AppointmentStatus status;
}