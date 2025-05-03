package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.Users;
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

    @PostMapping("/register")
    public ResponseEntity<PatientResponse> registerDoctor(@RequestBody PatientRegister doctorDTO) {
        PatientResponse savedPatient = patientService.registerPatient(doctorDTO);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> createAppointment(@RequestBody AppointmentCreateRequestByPatient request) {
        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppointmentResponse created = patientService.createAppointmentByPatient(currentUser.getId(),request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsForLoggedInPatient() {
        Users currentUser = (Users) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Long patientId = currentUser.getId();
        System.out.println(patientId);
        List<AppointmentResponse> appointments = patientService.getAppointmentsListByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> getAppointmentDetails(@PathVariable Long appointmentId) {
        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppointmentResponse dto = patientService.getAppointmentByPatient(currentUser.getId(), appointmentId);
        return ResponseEntity.ok(dto);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/test")
    public ResponseEntity<UserTestResponse> test() {

        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("-----------------------------------");
        log.warn("Current user: {}", currentUser.getUsername());
        log.warn("Current user role: {}", currentUser.getRole());
        log.warn("Current user id: {}", currentUser.getId());
        log.info("-----------------------------------");

        return ResponseEntity.ok(new UserTestResponse(
                currentUser.getId(),
                currentUser.getUsername(),
                currentUser.getRole())) ;
    }

}
