package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.*;
import com.Abhijith.HospitalManagementSystem.Repository.AppointmentRepository;
import com.Abhijith.HospitalManagementSystem.Repository.DoctorRepository;
import com.Abhijith.HospitalManagementSystem.Repository.PatientRepository;
import com.Abhijith.HospitalManagementSystem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
public class PatientService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepo;
    private final PasswordEncoder passwordEncoder;
    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    // Register Patient
    public PatientResponse registerPatient(PatientRegister dto) {
        // Check if username already exists
        Optional<Users> existingUser = userRepository.findByUsername(dto.getUsername());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        Users user = Users.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.ROLE_PATIENT)
                .isEnabled(true)
                .isAccountNonLocked(true)
                .build();

        Patient patient = Patient.builder()
                .name(dto.getName())
                .gender(dto.getGender())
                .contact(dto.getContact())
                .address(dto.getAddress())
                .age(dto.getAge())
                .user(user)
                .build();

        Patient savedPatient = patientRepo.save(patient);

        return new PatientResponse(
                savedPatient.getId(),
                savedPatient.getUser().getUsername(),
                savedPatient.getName(),
                savedPatient.getAddress(),
                savedPatient.getAge(),
                savedPatient.getGender(),
                savedPatient.getContact()
        );
    }

    public PatientAdminResponse updatePatientByUsername(String username, PatientUpdateRequest request) {
        Patient patient = patientRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        if (request.getName() != null) patient.setName(request.getName());
        if (request.getGender() != null) patient.setGender(request.getGender());
        if (request.getContact() != null) patient.setContact(request.getContact());
        if (request.getAddress() != null) patient.setAddress(request.getAddress());
        if (request.getAge() != null) patient.setAge(request.getAge());

        Patient updated = patientRepository.save(patient);

        return PatientAdminResponse.builder()
                .id(updated.getId())
                .name(updated.getName())
                .gender(updated.getGender())
                .contact(updated.getContact())
                .address(updated.getAddress())
                .age(updated.getAge())
                .username(updated.getUser().getUsername())
                .isEnabled(updated.getUser().isEnabled())
                .isAccountNonLocked(updated.getUser().isAccountNonLocked())
                .build();
    }



    public AppointmentResponse createAppointmentByPatient(Long patientId, AppointmentCreateRequestByPatient request) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));
        Patient patient = patientRepository.findByUserId(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(request.getAppointmentDate());
        appointment.setReason(request.getReason());
        appointment.setStatus(AppointmentStatus.PENDING);

        appointmentRepository.save(appointment);
        return toResponse(appointment);
    }

    public AppointmentResponse getAppointmentByPatient(Long id, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

        if (!appointment.getPatient().getId().equals(patient.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied to this appointment");
        }

        return toResponse(appointment);
    }

    public List<AppointmentResponse> getAppointmentsListByPatientId(Long userId) {
        Patient patient = patientRepository.findByUserId(userId).orElseThrow(()->new
                ResponseStatusException(HttpStatus.NOT_FOUND,"Patient not found"));
        List<Appointment> appointments = appointmentRepository.findByPatientId(patient.getId());
        return appointments.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

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
