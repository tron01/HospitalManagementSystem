package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.AppointmentRequest;
import com.Abhijith.HospitalManagementSystem.DTO.AppointmentResponse;
import com.Abhijith.HospitalManagementSystem.DTO.AppointmentStatusUpdateRequest;
import com.Abhijith.HospitalManagementSystem.Service.ReceptionistService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/receptionist")
@RequiredArgsConstructor
public class ReceptionistController {

	private final ReceptionistService receptionistService;

	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/appointments")
	public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request) {
		return ResponseEntity.ok(receptionistService.createAppointment(request));
	}

	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/appointments/status")
	public ResponseEntity<AppointmentResponse> updateStatus(@RequestBody AppointmentStatusUpdateRequest request) {
		return ResponseEntity.ok(receptionistService.updateAppointmentStatus(request));
	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/appointments")
	public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
		return ResponseEntity.ok(receptionistService.getAllAppointments());
	}
}
