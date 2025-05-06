package com.Abhijith.HospitalManagementSystem.Service;
import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.*;
import com.Abhijith.HospitalManagementSystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final UserRepository userRepository;
	private final PatientRepository patientRepository;
	private final DoctorRepository doctorRepository;
	private final ReceptionistRepository receptionistRepository;
	private final AppointmentRepository appointmentRepository;
	private final UserRepository usersRepository;

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
		Role roleEnum = Role.valueOf(role.toUpperCase()); // Convert string to Role enum (case insensitive)
		return userRepository.findByRole(roleEnum).stream()
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

	public DoctorAdminResponse updateDoctor(Long id, DoctorUpdateRequest request) {
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor not found"));

		Users user = doctor.getUser();

		// Check if the username is already taken by someone else
		if (request.getUsername() != null) {
			Optional<Users> existingUser = userRepository.findByUsername(request.getUsername());
			if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists for another doctor. Please choose a different username.");
			}
			user.setUsername(request.getUsername());
		}

		// Update doctor info conditionally
		if (request.getName() != null) doctor.setName(request.getName());
		if (request.getSpecialization() != null) doctor.setSpecialization(request.getSpecialization());
		if (request.getContact() != null) doctor.setContact(request.getContact());
		if (request.getEmail() != null) doctor.setEmail(request.getEmail());

		return toDoctorAdminResponse(doctorRepository.save(doctor));
	}

	public ReceptionistAdminResponse updateReceptionist(Long id, ReceptionistUpdateRequest request) {
		Receptionist receptionist = receptionistRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Receptionist not found"));

		Users user = receptionist.getUser();

		// Check if the username is already taken by someone else
		if (request.getUsername() != null) {
			Optional<Users> existingUser = userRepository.findByUsername(request.getUsername());
			if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists for another user. Please choose a different username.");
			}
			user.setUsername(request.getUsername());
		}

		// Update receptionist info conditionally
		if (request.getName() != null) receptionist.setName(request.getName());
		if (request.getPhone() != null) receptionist.setPhone(request.getPhone());
		if (request.getEmail() != null) receptionist.setEmail(request.getEmail());

		// Save user update (if username changed)
		userRepository.save(user);
		Receptionist updated = receptionistRepository.save(receptionist);

		return new ReceptionistAdminResponse(
				updated.getId(),
				updated.getName(),
				updated.getPhone(),
				updated.getEmail(),
				updated.getUser().getUsername()
		);
	}


	public PatientAdminResponse updatePatient(Long id, PatientUpdateRequest request) {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

		Users user = patient.getUser();

		// Check if username is being updated and ensure it's unique
		if (request.getUsername() != null) {
			Optional<Users> existingUser = userRepository.findByUsername(request.getUsername());
			if (existingUser.isPresent() && !existingUser.get().getId().equals(user.getId())) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists for another patient. Please choose a different username.");
			}
			user.setUsername(request.getUsername());
		}

		// Update patient info conditionally
		if (request.getName() != null) patient.setName(request.getName());
		if (request.getAge() != null) patient.setAge(request.getAge());
		if (request.getGender() != null) patient.setGender(request.getGender());
		if (request.getContact() != null) patient.setContact(request.getContact());
		if (request.getAddress() != null) patient.setAddress(request.getAddress());

		return toPatientAdminResponse(patientRepository.save(patient));
	}

	public AdminDashboardDTO getDashboardData() {
		long patients = patientRepository.count();
		long doctors = doctorRepository.count();
		long appointments = appointmentRepository.count();
		long receptionists = receptionistRepository.count();
		long users = usersRepository.count();

		long scheduled = appointmentRepository.countByStatus(AppointmentStatus.CONFIRMED);
		long completed = appointmentRepository.countByStatus(AppointmentStatus.COMPLETED);
		long cancelled = appointmentRepository.countByStatus(AppointmentStatus.CANCELLED);
		long pending = appointmentRepository.countByStatus(AppointmentStatus.PENDING);

		return new AdminDashboardDTO(
				patients, doctors, appointments, receptionists, users,
				scheduled, completed, cancelled, pending
		);
	}


	private UserResponse toDto(Users user) {
			return UserResponse.builder()
					.id(user.getId())
					.username(user.getUsername())
					.role(user.getRole().name())
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
