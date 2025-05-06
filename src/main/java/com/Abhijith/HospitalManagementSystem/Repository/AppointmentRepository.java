package com.Abhijith.HospitalManagementSystem.Repository;

import com.Abhijith.HospitalManagementSystem.Model.Appointment;
import com.Abhijith.HospitalManagementSystem.Model.AppointmentStatus;
import com.Abhijith.HospitalManagementSystem.Model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	List<Appointment> findByPatientId(Long patientId);
	List<Appointment> findByDoctorId(Long doctorId);
	List<Appointment> findByStatus(AppointmentStatus status);

	// Count total appointments by doctor
	long countByDoctor(Doctor doctor);

	// Count appointments by status for a doctor
	long countByDoctorAndStatus(Doctor doctor, AppointmentStatus status);

	// Count today's appointments by doctor
	long countByDoctorAndAppointmentTimeBetween(Doctor doctor, LocalDateTime start, LocalDateTime end);

	// Count today's appointments by status for a doctor
	long countByDoctorAndStatusAndAppointmentTimeBetween(Doctor doctor, AppointmentStatus status, LocalDateTime start, LocalDateTime end);

	long countByStatus(AppointmentStatus status);
}
