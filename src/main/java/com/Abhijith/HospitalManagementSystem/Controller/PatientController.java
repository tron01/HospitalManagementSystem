package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.AppointmentResponse;
import com.Abhijith.HospitalManagementSystem.DTO.PatientRegister;
import com.Abhijith.HospitalManagementSystem.DTO.PatientResponse;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Service.AppointmentService;
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
    private final AppointmentService appointmentService;

    @PostMapping("/register")
    public ResponseEntity<PatientResponse> registerDoctor(@RequestBody PatientRegister doctorDTO) {
        PatientResponse savedPatient = patientService.registerPatient(doctorDTO);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsForLoggedInPatient() {
        Users currentUser = (Users) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Long patientId = currentUser.getId();
        List<AppointmentResponse> appointments = appointmentService.getAppointmentsByPatientId(patientId);
        return ResponseEntity.ok(appointments);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/test")
    public String test() {
        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = currentUser.getId();
        String role = currentUser.getRole();
        log.info("User with id:{} and Role : {} is trying to access patient test", userId,role);
        return "patient api reachable";
    }


}
