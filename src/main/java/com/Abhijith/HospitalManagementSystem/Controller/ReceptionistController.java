package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.AppointmentRequest;
import com.Abhijith.HospitalManagementSystem.DTO.AppointmentResponse;
import com.Abhijith.HospitalManagementSystem.DTO.AppointmentStatusUpdateRequest;
import com.Abhijith.HospitalManagementSystem.DTO.UserTestResponse;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Service.AppointmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/receptionist")
@RequiredArgsConstructor
public class ReceptionistController {

	private final AppointmentService appointmentService;

	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/appointments")
	public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request) {
		return ResponseEntity.ok(appointmentService.createAppointment(request));
	}

	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/appointments/status")
	public ResponseEntity<AppointmentResponse> updateStatus(@RequestBody AppointmentStatusUpdateRequest request) {
		return ResponseEntity.ok(appointmentService.updateAppointmentStatus(request));
	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/appointments")
	public ResponseEntity<List<AppointmentResponse>> getAppointmentsByStatus(@RequestParam String status) {
		return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(status));
	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/test")
	public ResponseEntity<UserTestResponse> test() {

		Users currentUser = getLoggedUserInfo();

		log.info("-----------------------------------");
		log.warn("Current user: {}", currentUser.getUsername());
		log.warn("Current user role: {}", currentUser.getRole());
		log.warn("Current user id: {}", currentUser.getId());
		log.info("-----------------------------------");

		return ResponseEntity.ok(new UserTestResponse(
				currentUser.getId(),
				currentUser.getUsername(),
				currentUser.getRole().name())) ;
	}

	private static Users getLoggedUserInfo() {
		return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

}
