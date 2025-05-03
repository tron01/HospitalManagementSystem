package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.DoctorRegister;
import com.Abhijith.HospitalManagementSystem.DTO.DoctorResponse;
import com.Abhijith.HospitalManagementSystem.Model.Doctor;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Repository.DoctorRepository;
import com.Abhijith.HospitalManagementSystem.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class DoctorService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;



    // Register Doctor
    public DoctorResponse registerDoctor(DoctorRegister dto) {
        // Check if username already exists
        Optional<Users> existingUser = userRepository.findByUsername(dto.getUsername());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Username already exists");
        }

        // Create User entity
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ROLE_DOCTOR"); // Set role for doctor
        user.setEnabled(false);
        user.setAccountNonLocked(true);
        userRepository.save(user);

        // Create Doctor entity
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setContact(dto.getContact());
        doctor.setEmail(dto.getEmail());
        doctor.setUser(user); // Link doctor with user

        // Save doctor and return response DTO
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

    //getList
    public List<DoctorResponse> getAllDoctors() {
        return  doctorRepository.findAll().stream().map(d->new DoctorResponse(d.getId(),
                        d.getUser().getUsername(),
                        d.getName(),
                        d.getSpecialization(),
                        d.getEmail(),
                        d.getContact()))
                .toList();
    }

    //getByID
    public DoctorResponse getDoctorById(long id) {
        Doctor doctor = doctorRepository.findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor with Id "+id+" not found"));
        return new DoctorResponse(
                doctor.getId(),
                doctor.getUser().getUsername(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getEmail(),
                doctor.getContact());
    }
}