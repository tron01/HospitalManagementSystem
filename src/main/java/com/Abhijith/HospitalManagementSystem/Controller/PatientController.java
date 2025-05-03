package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.PatientRegister;
import com.Abhijith.HospitalManagementSystem.DTO.PatientResponse;
import com.Abhijith.HospitalManagementSystem.Service.PatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    @GetMapping("/list")
    public ResponseEntity<List<PatientResponse>> getPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/test")
    public String test() {
        return "patient api reachable";
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable("id") long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

}
