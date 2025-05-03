package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.AppointmentCreateRequestByPatient;
import com.Abhijith.HospitalManagementSystem.DTO.AppointmentResponse;
import com.Abhijith.HospitalManagementSystem.DTO.PatientRegister;
import com.Abhijith.HospitalManagementSystem.DTO.PatientResponse;
import com.Abhijith.HospitalManagementSystem.Model.Appointment;
import com.Abhijith.HospitalManagementSystem.Model.Doctor;
import com.Abhijith.HospitalManagementSystem.Model.Patient;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Repository.AppointmentRepository;
import com.Abhijith.HospitalManagementSystem.Repository.DoctorRepository;
import com.Abhijith.HospitalManagementSystem.Repository.PatientRepository;
import com.Abhijith.HospitalManagementSystem.Repository.UserRepository;
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
            throw new RuntimeException("Username already exists");
        }

        // Create User entity
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ROLE_PATIENT");
        userRepository.save(user);

        // Create Patient entity
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setUser(user);
        patient.setAddress(dto.getAddress());
        patient.setAge(dto.getAge());
        patient.setGender(dto.getGender());
        patient.setContact(dto.getContact());
        Patient savedPatient = patientRepo.save(patient);

        // Save patient and return response DTO
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
        appointment.setStatus("PENDING");

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
