package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDashboardResponse {

	private long totalAppointments;
	private long pendingAppointments;
	private long completedAppointments;
	private long scheduledAppointments;

	private long totalTodayAppointments;
	private long pendingTodayAppointments;
	private long completedTodayAppointments;
	private long scheduledTodayAppointments;

}