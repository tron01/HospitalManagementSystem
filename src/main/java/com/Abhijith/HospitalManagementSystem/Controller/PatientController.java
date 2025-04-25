package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.PatientRegister;
import com.Abhijith.HospitalManagementSystem.DTO.PatientResponse;
import com.Abhijith.HospitalManagementSystem.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/register")
    public ResponseEntity<PatientResponse> registerDoctor(@RequestBody PatientRegister doctorDTO) {
        PatientResponse savedPatient = patientService.registerPatient(doctorDTO);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PatientResponse>> getPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/test")
    public String test() {
        return "patient api reachable";
    }

}
