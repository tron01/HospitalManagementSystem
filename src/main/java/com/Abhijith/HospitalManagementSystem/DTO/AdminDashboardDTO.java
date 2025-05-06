package com.Abhijith.HospitalManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardDTO {
	private long totalPatients;
	private long totalDoctors;
	private long totalAppointments;
	private long totalReceptionists;
	private long totalUsers;

	private long scheduledAppointments;
	private long completedAppointments;
	private long cancelledAppointments;
	private long pendingAppointments;
}