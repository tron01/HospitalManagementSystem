package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.*;
import com.Abhijith.HospitalManagementSystem.Repository.AppointmentRepository;
import com.Abhijith.HospitalManagementSystem.Repository.DoctorRepository;
import com.Abhijith.HospitalManagementSystem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppointmentRepository appointmentRepository;

    // Register Doctor
    public DoctorResponse registerDoctor(DoctorRegister dto) {

        Optional<Users> existingUser = userRepository.findByUsername(dto.getUsername());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        Users user = Users.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.ROLE_DOCTOR)
                .isEnabled(true)
                .isAccountNonLocked(true)
                .build();

        Doctor doctor = Doctor.builder()
                .name(dto.getName())
                .specialization(dto.getSpecialization())
                .contact(dto.getContact())
                .email(dto.getEmail())
                .user(user)
                .build();

        Doctor savedDoctor = doctorRepository.save(doctor);

        return new DoctorResponse(
                savedDoctor.getId(),
                savedDoctor.getUser().getUsername(),
                savedDoctor.getName(),
                savedDoctor.getSpecialization(),
                savedDoctor.getEmail(),
                savedDoctor.getContact()
        );
    }

    public DoctorResponse updateDoctorInfo(String currentUser, DoctorRequest doctorRequest) {

        Doctor doctor = doctorRepository.findByUserUsername(currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));

        // Update fields only if provided (not null)
        if (doctorRequest.getName() != null) doctor.setName(doctorRequest.getName());
        if (doctorRequest.getSpecialization() != null) doctor.setSpecialization(doctorRequest.getSpecialization());
        if (doctorRequest.getContact() != null) doctor.setContact(doctorRequest.getContact());
        if (doctorRequest.getEmail() != null) doctor.setEmail(doctorRequest.getEmail());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return new DoctorResponse(
                updatedDoctor.getId(),
                updatedDoctor.getUser().getUsername(),
                updatedDoctor.getName(),
                updatedDoctor.getSpecialization(),
                updatedDoctor.getEmail(),
                updatedDoctor.getContact()
        );
    }

    public List<AppointmentResponse> getAppointmentsListByDoctorId(Long userId) {

        Doctor doctor = doctorRepository.findByUserId(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "doctor not found"));

        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctor.getId());

        return appointments.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public DoctorDashboardResponse getDashboardData(String currentUser) {
        // Fetch doctor by user's ID
        Doctor doctor = doctorRepository.findByUserUsername(currentUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found"));

        // Get today's date and calculate start and end of the day
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        // Get total appointments
        long totalAppointments = appointmentRepository.countByDoctor(doctor);

        // Get total appointments by status
        long pendingAppointments = appointmentRepository.countByDoctorAndStatus(doctor, AppointmentStatus.PENDING);
        long completedAppointments = appointmentRepository.countByDoctorAndStatus(doctor, AppointmentStatus.COMPLETED);
        long scheduledAppointments = appointmentRepository.countByDoctorAndStatus(doctor, AppointmentStatus.CONFIRMED);

        // Get today's appointments
        long totalTodayAppointments = appointmentRepository.countByDoctorAndAppointmentTimeBetween(doctor, startOfDay, endOfDay);
        long pendingTodayAppointments = appointmentRepository.countByDoctorAndStatusAndAppointmentTimeBetween(doctor, AppointmentStatus.PENDING, startOfDay, endOfDay);
        long completedTodayAppointments = appointmentRepository.countByDoctorAndStatusAndAppointmentTimeBetween(doctor, AppointmentStatus.COMPLETED, startOfDay, endOfDay);
        long scheduledTodayAppointments = appointmentRepository.countByDoctorAndStatusAndAppointmentTimeBetween(doctor, AppointmentStatus.CONFIRMED, startOfDay, endOfDay);

        // Return the response object
        return new DoctorDashboardResponse(totalAppointments, pendingAppointments, completedAppointments, scheduledAppointments,
                totalTodayAppointments, pendingTodayAppointments, completedTodayAppointments, scheduledTodayAppointments);
    }

    //----------------------MAPPER-------------------------//

    private AppointmentResponse toResponse(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatient().getName(),
                appointment.getDoctor().getName(),
                appointment.getAppointmentTime(),
                appointment.getReason(),
                appointment.getStatus()
        );
    }

}