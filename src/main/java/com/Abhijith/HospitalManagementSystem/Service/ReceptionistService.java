package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.*;
import com.Abhijith.HospitalManagementSystem.Model.*;
import com.Abhijith.HospitalManagementSystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceptionistService {

	private final AppointmentRepository appointmentRepository;
	private final PatientRepository patientRepository;
	private final DoctorRepository doctorRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final ReceptionistRepository receptionistRepository;

	public List<AppointmentResponse> getAllAppointments() {
		return appointmentRepository.findAll().stream()
				.map(this::toAppointmentDto)
				.collect(Collectors.toList());
	}

	public AppointmentResponse createAppointment(AppointmentRequest request) {

		Patient patient = patientRepository.findById(request.getPatientId())
				.orElseThrow(() -> new RuntimeException("Patient not found"));
		Doctor doctor = doctorRepository.findById(request.getDoctorId())
				.orElseThrow(() -> new RuntimeException("Doctor not found"));

		Appointment appointment = Appointment.builder()
				.patient(patient)
				.doctor(doctor)
				.appointmentTime(request.getDateTime())
				.reason(request.getReason())
				.status("PENDING")
				.build();

		return toAppointmentDto(appointmentRepository.save(appointment));
	}

	public AppointmentResponse updateAppointmentStatus(AppointmentStatusUpdateRequest request) {
		Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
				.orElseThrow(() -> new RuntimeException("Appointment not found"));

		appointment.setStatus(request.getStatus());
		return toAppointmentDto(appointmentRepository.save(appointment));
	}

	public ReceptionistResponse createReceptionist(CreateReceptionistRequest request) {
		Users user = Users.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.ROLE_RECEPTIONIST)
				.build();
		userRepository.save(user);

		Receptionist receptionist = Receptionist.builder()
				.name(request.getName())
				.email(request.getEmail())
				.phone(request.getPhone())
				.user(user)
				.build();
		return toReceptionistDto(receptionistRepository.save(receptionist));
	}
	// ---------- MAPPERS ----------
	private AppointmentResponse toAppointmentDto(Appointment a) {
		return AppointmentResponse.builder()
				.id(a.getId())
				.dateTime(a.getAppointmentTime())
				.reason(a.getReason())
				.Status(a.getStatus())
				.doctorName(a.getDoctor().getName())
				.patientName(a.getPatient().getName())
				.build();
	}

	private ReceptionistResponse toReceptionistDto(Receptionist r) {
		return ReceptionistResponse.builder()
				.id(r.getId())
				.name(r.getName())
				.email(r.getEmail())
				.phone(r.getPhone())
				.username(r.getUser().getUsername())
				.build();
	}
}
