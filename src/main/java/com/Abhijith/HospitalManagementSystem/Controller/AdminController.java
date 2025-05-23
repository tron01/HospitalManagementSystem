package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.AppointmentStatus;
import com.Abhijith.HospitalManagementSystem.Model.PaymentStatus;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Service.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
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
	private final ReceptionistService receptionistService;
	private final AppointmentService appointmentService;
	private final BillingService billingService;
	private final DoctorNoteService doctorNoteService;

	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/billings")
	public ResponseEntity<BillingResponse> createBilling(@Valid @RequestBody BillingRequest request) {
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
	public ResponseEntity<BillingResponse> updatePaymentStatus(@PathVariable Long appointmentId,@RequestParam PaymentStatus status) {
		return ResponseEntity.ok(billingService.updatePaymentStatusByAppointmentId(appointmentId, status));
	}

	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/doctors/create-user")
	public ResponseEntity<DoctorResponse> createDoctor(@Valid @RequestBody DoctorRegister request) {
		// Logic to create doctor
		DoctorResponse savedDoctor = doctorService.registerDoctor(request);
		return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/patients/create-user")
	public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientRegister request) {
		// Logic to create Patient
		PatientResponse savedPatient = patientService.registerPatient(request);
		return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
	}

	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/receptionists/create-user")
	public ResponseEntity<ReceptionistResponse > createReceptionist(@Valid @RequestBody CreateReceptionistRequest request) {
		// Logic to create Patient
		ReceptionistResponse receptionist = receptionistService.createReceptionist(request);
		return ResponseEntity.ok(receptionist);
	}

	@SecurityRequirement(name = "bearerAuth")
	@PostMapping("/appointments")
	public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
		return ResponseEntity.ok(appointmentService.createAppointment(request));
	}

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/appointments")
	public ResponseEntity<List<AppointmentResponse>> getAllAppointments(@RequestParam(required = false) AppointmentStatus status) {
		if (status != null) {
			return ResponseEntity.ok(appointmentService.getAppointmentsByStatus(status));
		} else {
			return ResponseEntity.ok(appointmentService.getAllAppointments());
		}
	}

	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/appointments/{id}/status")
	public ResponseEntity<AppointmentResponse> updateStatus(@PathVariable Long id,@Valid  @RequestBody AppointmentStatusUpdateRequest request) {
		return ResponseEntity.ok(appointmentService.updateAppointmentStatus(id, request));
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
	public ResponseEntity<AdminDashboardDTO> getDashboardData() {
		AdminDashboardDTO dashboard = adminService.getDashboardData();
		return ResponseEntity.ok(dashboard);
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
	@GetMapping("/receptionists")
	public ResponseEntity<List<ReceptionistResponse>> getAllReceptionists() {
		return ResponseEntity.ok(receptionistService.getAllReceptionists());
	}

	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/receptionists/{id}")
	public ResponseEntity<ReceptionistAdminResponse> updateReceptionist(@PathVariable Long id,@Valid @RequestBody ReceptionistUpdateRequest request) {
		ReceptionistAdminResponse updatedReceptionist = adminService.updateReceptionist(id, request);
		return ResponseEntity.ok(updatedReceptionist);
	}

	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/doctors/{id}")
	public ResponseEntity<DoctorAdminResponse> updateDoctor(@PathVariable Long id,@Valid @RequestBody DoctorUpdateRequest request) {
		return ResponseEntity.ok(adminService.updateDoctor(id, request));
	}

	@SecurityRequirement(name = "bearerAuth")
	@PutMapping("/patients/{id}")
	public ResponseEntity<PatientAdminResponse> updatePatient(@PathVariable Long id,@Valid @RequestBody PatientUpdateRequest request) {
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

	@SecurityRequirement(name = "bearerAuth")
	@GetMapping("/appointment{appointmentId}/doctor-notes")
	public ResponseEntity<DoctorNoteResponse> getDoctorNoteByAppointmentId(@PathVariable Long appointmentId) {
		return ResponseEntity.ok(doctorNoteService.getDoctorNoteByAppointmentId(appointmentId));
	}

}
