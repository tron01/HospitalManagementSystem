package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Service.BillingService;
import com.Abhijith.HospitalManagementSystem.Service.DoctorNoteService;
import com.Abhijith.HospitalManagementSystem.Service.PatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/patient")
@AllArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final DoctorNoteService doctorNoteService;
    private final BillingService billingService;

    @PostMapping("/register")
    public ResponseEntity<PatientResponse> registerDoctor(@RequestBody PatientRegister doctorDTO) {
        PatientResponse savedPatient = patientService.registerPatient(doctorDTO);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

//    @SecurityRequirement(name = "bearerAuth")
//    @PostMapping("/appointments")
//    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentCreateRequestByPatient request) {
//        Users currentUser = getLoggedUserInfo();
//        AppointmentResponse created = patientService.createAppointmentByPatient(currentUser.getId(),request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/update")
    public ResponseEntity<PatientAdminResponse> updateOwnPatientInfo(@RequestBody PatientUpdateRequest request) {
        Users currentUser = getLoggedUserInfo(); // Method that fetches the logged-in user
        return ResponseEntity.ok(patientService.updatePatientByUsername(currentUser.getUsername(), request));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsForLoggedInPatient() {
        Users currentUser = getLoggedUserInfo();

        Long patientId = currentUser.getId();
        System.out.println(patientId);
        List<AppointmentResponse> appointments = patientService.getAppointmentsListByPatientId(patientId);
        return ResponseEntity.ok(appointments);
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
    @GetMapping("/appointment/{appointmentId}/billinfo")
    public ResponseEntity<BillingResponse> getBillingByAppointmentId(@PathVariable Long appointmentId) {

        Users currentUser = getLoggedUserInfo();// From JWT/session
        BillingResponse billingDTO = billingService.getBillingByAppointmentId(appointmentId, currentUser.getUsername());
        return ResponseEntity.ok(billingDTO);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/appointment/{appointmentId}/doc_note")
    public ResponseEntity<DoctorNoteResponse> getDoctorNote(@PathVariable Long appointmentId) {

        Users currentUser = getLoggedUserInfo();// From JWT/session
        DoctorNoteResponse note = doctorNoteService.getDoctorNoteByAppointmentIdForPatient(appointmentId, currentUser.getUsername());
        return ResponseEntity.ok(note);
    }


}
