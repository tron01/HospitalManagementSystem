package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Service.AdminService;
import com.Abhijith.HospitalManagementSystem.Service.DoctorService;
import com.Abhijith.HospitalManagementSystem.Service.PatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	private final DoctorService doctorService;
	private final PatientService patientService;

	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/doctors/create-doctor")
	public ResponseEntity<DoctorResponse> createDoctor(@RequestBody DoctorRegister request) {
		// Logic to create doctor
		DoctorResponse savedDoctor = doctorService.registerDoctor(request);
		return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/patients/create-patient")
	public ResponseEntity<PatientResponse> createPatient(@RequestBody PatientRegister request) {
		// Logic to create Patient
		PatientResponse savedPatient = patientService.registerPatient(request);
		return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
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
	@GetMapping("/dashboard")
	public String adminDashboard() {
		return "Welcome to the admin dashboard!";
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
}
