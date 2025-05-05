package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.AppointmentStatus;
import com.Abhijith.HospitalManagementSystem.Model.PaymentStatus;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Service.AppointmentService;
import com.Abhijith.HospitalManagementSystem.Service.BillingService;
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
	private final BillingService billingService;

	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/billings")
	public ResponseEntity<BillingResponse> createBilling(@RequestBody BillingRequest request) {
		return ResponseEntity.ok(billingService.createBilling(request));
	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/billings")
	public ResponseEntity<List<BillingResponse>> getBillingsByStatus(@RequestParam(required = false) PaymentStatus status) {
		if (status != null) {
			return ResponseEntity.ok(billingService.getBillingsByStatus(status));
		} else {
			return ResponseEntity.ok(billingService.getAllBillings());
		}
	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/billings/{id}")
	public ResponseEntity<BillingResponse> getBillingById(@PathVariable Long id) {
		return ResponseEntity.ok(billingService.getBillingById(id));
	}

	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/appointment/{appointmentId}/payment")
	public ResponseEntity<BillingResponse> updatePaymentStatus(
			@PathVariable Long appointmentId,
			@RequestParam PaymentStatus status) {
		return ResponseEntity.ok(billingService.updatePaymentStatusByAppointmentId(appointmentId, status));
	}
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/appointments")
	public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentRequest request) {
		return ResponseEntity.ok(appointmentService.createAppointment(request));
	}

	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/appointments/{id}/status")
	public ResponseEntity<AppointmentResponse> updateStatus(@PathVariable Long id, @RequestBody AppointmentStatusUpdateRequest request) {
		return ResponseEntity.ok(appointmentService.updateAppointmentStatus(id, request));
	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/appointments")
	public ResponseEntity<List<AppointmentResponse>> getAppointmentsByStatus(@RequestParam AppointmentStatus status) {
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
