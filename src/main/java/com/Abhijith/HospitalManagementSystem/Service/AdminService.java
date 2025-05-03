package com.Abhijith.HospitalManagementSystem.Service;
import com.Abhijith.HospitalManagementSystem.DTO.DoctorAdminResponse;
import com.Abhijith.HospitalManagementSystem.DTO.PatientAdminResponse;
import com.Abhijith.HospitalManagementSystem.DTO.UserResponse;
import com.Abhijith.HospitalManagementSystem.Model.Doctor;
import com.Abhijith.HospitalManagementSystem.Model.Patient;
import com.Abhijith.HospitalManagementSystem.Model.Users;
import com.Abhijith.HospitalManagementSystem.Repository.DoctorRepository;
import com.Abhijith.HospitalManagementSystem.Repository.PatientRepository;
import com.Abhijith.HospitalManagementSystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

private final UserRepository userRepository;
private final PatientRepository patientRepository;
private final DoctorRepository doctorRepository;


	public UserResponse enableUser(Long userId) {
		Users user = getUserOrThrow(userId);
		user.setEnabled(true);
		return toDto(userRepository.save(user));
	}

	public UserResponse disableUser(Long userId) {
		Users user = getUserOrThrow(userId);
		user.setEnabled(false);
		return toDto(userRepository.save(user));
	}

	public UserResponse lockUser(Long userId) {
		Users user = getUserOrThrow(userId);
		user.setAccountNonLocked(false);
		return toDto(userRepository.save(user));
	}

	public UserResponse unlockUser(Long userId) {
		Users user = getUserOrThrow(userId);
		user.setAccountNonLocked(true);
		return toDto(userRepository.save(user));
	}

	private Users getUserOrThrow(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
	}
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll().stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	public List<UserResponse> getUsersByRole(String role) {
		return userRepository.findByRoleIgnoreCase(role).stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}
	public List<PatientAdminResponse> getAllPatients() {
		return patientRepository.findAll().stream()
				.map(this::toPatientAdminResponse)
				.collect(Collectors.toList());
	}

	public List<DoctorAdminResponse> getAllDoctors() {
		return doctorRepository.findAll().stream()
				.map(this::toDoctorAdminResponse)
				.collect(Collectors.toList());
	}

	private UserResponse toDto(Users user) {
			return UserResponse.builder()
					.id(user.getId())
					.username(user.getUsername())
					.role(user.getRole())
					.isEnabled(user.isEnabled())
					.isAccountNonLocked(user.isAccountNonLocked())
					.build();
	}

	private PatientAdminResponse toPatientAdminResponse(Patient patient) {
		Users user = patient.getUser();
		return PatientAdminResponse.builder()
				.id(patient.getId())
				.name(patient.getName())
				.username(user.getUsername())
				.age(patient.getAge())
				.gender(patient.getGender())
				.contact(patient.getContact())
				.address(patient.getAddress())
				.isEnabled(user.isEnabled())
				.isAccountNonLocked(user.isAccountNonLocked())
				.build();
	}
	private DoctorAdminResponse toDoctorAdminResponse(Doctor doctor) {
		Users user = doctor.getUser();
		return DoctorAdminResponse.builder()
				.id(doctor.getId())
				.name(doctor.getName())
				.username(user.getUsername())
				.specialization(doctor.getSpecialization())
				.isEnabled(user.isEnabled())
				.isAccountNonLocked(user.isAccountNonLocked())
				.build();
	}


}
