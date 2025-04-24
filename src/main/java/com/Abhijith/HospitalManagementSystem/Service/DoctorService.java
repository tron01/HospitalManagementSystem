package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.DoctorRegister;
import com.Abhijith.HospitalManagementSystem.DTO.DoctorResponse;
import com.Abhijith.HospitalManagementSystem.Model.Doctor;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Repository.DoctorRepository;
import com.Abhijith.HospitalManagementSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorService(UserRepository userRepository, DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register Doctor
    public DoctorResponse registerDoctor(DoctorRegister dto) {
        // Check if username already exists
        Optional<Users> existingUser = userRepository.findByUsername(dto.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create User entity
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("DOCTOR"); // Set role for doctor
        userRepository.save(user);

        // Create Doctor entity
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setUser(user); // Link doctor with user

        // Save doctor and return response DTO
        Doctor savedDoctor = doctorRepository.save(doctor);
        return new DoctorResponse(
                savedDoctor.getId(),
                savedDoctor.getUser().getUsername(),
                savedDoctor.getName(),
                savedDoctor.getSpecialization()
        );
    }
}
