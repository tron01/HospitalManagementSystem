package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.AppointmentRequest;
import com.Abhijith.HospitalManagementSystem.DTO.AppointmentResponse;
import com.Abhijith.HospitalManagementSystem.DTO.AppointmentStatusUpdateRequest;
import com.Abhijith.HospitalManagementSystem.Model.Appointment;
import com.Abhijith.HospitalManagementSystem.Model.Doctor;
import com.Abhijith.HospitalManagementSystem.Model.Patient;
import com.Abhijith.HospitalManagementSystem.Repository.AppointmentRepository;
import com.Abhijith.HospitalManagementSystem.Repository.DoctorRepository;
import com.Abhijith.HospitalManagementSystem.Repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final PatientRepository patientRepository;
	private final DoctorRepository doctorRepository;

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
}
