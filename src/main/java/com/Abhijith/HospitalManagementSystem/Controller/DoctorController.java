package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.DoctorRegister;
import com.Abhijith.HospitalManagementSystem.DTO.DoctorResponse;
import com.Abhijith.HospitalManagementSystem.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/register")
    public ResponseEntity<DoctorResponse> registerDoctor(@RequestBody DoctorRegister doctorDTO) {
        DoctorResponse savedDoctor = doctorService.registerDoctor(doctorDTO);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
    }
    @GetMapping("/list")
    public ResponseEntity<List<DoctorResponse>> getDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
    @GetMapping("/test")
    public String test() {
        return "doctor api reachable";
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable("id") long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }
}

