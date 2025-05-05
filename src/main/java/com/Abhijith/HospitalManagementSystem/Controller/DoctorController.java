package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Service.DoctorNoteService;
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
    private final DoctorNoteService doctorNoteService;

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
                currentUser.getRole().name()));
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

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/update")
    public ResponseEntity<DoctorResponse> updateDoctorInfo(@RequestBody DoctorRequest doctorRequest) {
        Users currentUser = getLoggedUserInfo();
        return ResponseEntity.ok(doctorService.updateDoctorInfo(currentUser.getUsername(), doctorRequest));
    }

    private static Users getLoggedUserInfo() {
        return (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/appointments/{appointmentId}/note")
    public ResponseEntity<DoctorNoteResponse> saveNote(@PathVariable Long appointmentId,@RequestBody DoctorNoteRequest request) {

        Users currentUser = (Users) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        // Set appointmentId from URL
        request.setAppointmentId(appointmentId);
        // check if principal.getName() (username) matches doctor of the appointment
        DoctorNoteResponse response = doctorNoteService.saveDoctorNote(request, currentUser.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/appointments/{appointmentId}/note")
    public ResponseEntity<DoctorNoteResponse> getDoctorNoteByAppointmentId(
            @PathVariable Long appointmentId) {

        Users currentUser = (Users) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        DoctorNoteResponse response = doctorNoteService.getDoctorNoteByAppointmentId(appointmentId, currentUser.getUsername());

        return ResponseEntity.ok(response);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/dashboard")
    public ResponseEntity<DoctorDashboardResponse> getDoctorDashboard() {
        // Get current logged in user (doctor)
        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Get dashboard data for the doctor
        DoctorDashboardResponse dashboardResponse = doctorService.getDashboardData(currentUser.getUsername());
        return ResponseEntity.ok(dashboardResponse);
    }


}

