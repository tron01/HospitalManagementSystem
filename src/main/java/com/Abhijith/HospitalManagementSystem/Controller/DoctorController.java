package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.DoctorRegister;
import com.Abhijith.HospitalManagementSystem.DTO.DoctorResponse;
import com.Abhijith.HospitalManagementSystem.DTO.UserTestResponse;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Service.DoctorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/list")
    public ResponseEntity<List<DoctorResponse>> getDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable("id") long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }

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
                currentUser.getRole()));
    }
}

