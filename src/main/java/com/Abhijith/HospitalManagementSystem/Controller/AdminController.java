package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Service.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/users")
	public ResponseEntity<List<UserResponse>> getUsers(@RequestParam(required = false) String role) {
		if (role != null && !role.isBlank()) {
			return ResponseEntity.ok(adminService.getUsersByRole(role));
		} else {
			return ResponseEntity.ok(adminService.getAllUsers());
		}
	}

	@SecurityRequirement(name = "bearerAuth")
	@PatchMapping("/users/{id}/enable")
	public ResponseEntity<UserResponse> enableUser(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.enableUser(id));
	}

	@SecurityRequirement(name = "bearerAuth")
	@PatchMapping("/users/{id}/disable")
	public ResponseEntity<UserResponse> disableUser(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.disableUser(id));
	}

	@SecurityRequirement(name = "bearerAuth")
	@PatchMapping("/users/{id}/lock")
	public ResponseEntity<UserResponse> lockUser(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.lockUser(id));
	}

	@SecurityRequirement(name = "bearerAuth")
	@PatchMapping("/users/{id}/unlock")
	public ResponseEntity<UserResponse> unlockUser(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.unlockUser(id));
	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/patients")
	public ResponseEntity<List<PatientAdminResponse>> getAllPatients() {
		return ResponseEntity.ok(adminService.getAllPatients());
	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/doctors")
	public ResponseEntity<List<DoctorAdminResponse>> getAllDoctors() {
		return ResponseEntity.ok(adminService.getAllDoctors());
	}
	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/doctors/{id}")
	public ResponseEntity<DoctorAdminResponse> updateDoctor(
			@PathVariable Long id,
			@RequestBody DoctorUpdateRequest request) {
		return ResponseEntity.ok(adminService.updateDoctor(id, request));
	}

	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/patients/{id}")
	public ResponseEntity<PatientAdminResponse> updatePatient(
			@PathVariable Long id,
			@RequestBody PatientUpdateRequest request) {
		return ResponseEntity.ok(adminService.updatePatient(id, request));
	}
}
