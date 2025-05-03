package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.PatientRegister;
import com.Abhijith.HospitalManagementSystem.DTO.PatientResponse;
import com.Abhijith.HospitalManagementSystem.Model.Patient;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Repository.PatientRepository;
import com.Abhijith.HospitalManagementSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class PatientService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PatientService(UserRepository userRepository, PatientRepository patientRepo, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.patientRepo = patientRepo;
        this.passwordEncoder = passwordEncoder;
    }

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

    //getByID
    public List<PatientResponse> getAllPatients() {
        return patientRepo.findAll().stream().map(p -> new PatientResponse(
                        p.getId(),
                        p.getUser().getUsername(),
                        p.getName(),
                        p.getAddress(),
                        p.getAge(),
                        p.getGender(),
                        p.getContact()))
                        .toList();
    }

    //getByID
    public PatientResponse getPatientById(long id) {
        Patient patient= patientRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient with Id "+id+" not found"));
        return new PatientResponse(patient.getId(),
                patient.getUser().getUsername(),
                patient.getName(),
                patient.getAddress(),
                patient.getAge(),
                patient.getGender(),
                patient.getContact());
    }
}
