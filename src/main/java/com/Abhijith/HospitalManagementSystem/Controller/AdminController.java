package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.UserResponse;
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
	@PatchMapping("/enable/{id}")
	public ResponseEntity<UserResponse> enableUser(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.enableUser(id));
	}

	@SecurityRequirement(name = "bearerAuth")
	@PatchMapping("/disable/{id}")
	public ResponseEntity<UserResponse> disableUser(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.disableUser(id));
	}
	@SecurityRequirement(name = "bearerAuth")
	@PatchMapping("/lock/{id}")
	public ResponseEntity<UserResponse> lockUser(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.lockUser(id));
	}
	@SecurityRequirement(name = "bearerAuth")
	@PatchMapping("/unlock/{id}")
	public ResponseEntity<UserResponse> unlockUser(@PathVariable Long id) {
		return ResponseEntity.ok(adminService.unlockUser(id));
	}
}
