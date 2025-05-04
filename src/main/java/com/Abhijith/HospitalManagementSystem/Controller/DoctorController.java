package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.AppointmentResponse;
import com.Abhijith.HospitalManagementSystem.DTO.DoctorRegister;
import com.Abhijith.HospitalManagementSystem.DTO.DoctorResponse;
import com.Abhijith.HospitalManagementSystem.DTO.UserTestResponse;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Service.DoctorService;
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
@RequestMapping("/api/doctor")
@AllArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/register")
    public ResponseEntity<DoctorResponse> registerDoctor(@RequestBody DoctorRegister doctorDTO) {
        DoctorResponse savedDoctor = doctorService.registerDoctor(doctorDTO);
        return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
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
                currentUser.getRole()));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsForLoggedInDoctor() {
        Users currentUser = (Users) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        Long doctorId = currentUser.getId();
        System.out.println(doctorId);
        List<AppointmentResponse> appointments = doctorService.getAppointmentsListByDoctorId(doctorId);
        System.out.println(appointments);
        return ResponseEntity.ok(appointments);
    }

    private static Users getLoggedUserInfo() {
        return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}

