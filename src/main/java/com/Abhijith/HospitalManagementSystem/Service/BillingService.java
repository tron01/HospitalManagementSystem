package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.BillingRequest;
import com.Abhijith.HospitalManagementSystem.DTO.BillingResponse;
import com.Abhijith.HospitalManagementSystem.DTO.DoctorNoteResponse;
import com.Abhijith.HospitalManagementSystem.DTO.MedicationDTO;
import com.Abhijith.HospitalManagementSystem.Model.*;
import com.Abhijith.HospitalManagementSystem.Repository.AppointmentRepository;
import com.Abhijith.HospitalManagementSystem.Repository.BillingRepository;
import com.Abhijith.HospitalManagementSystem.Repository.DoctorNoteRepository;
import com.Abhijith.HospitalManagementSystem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BillingService {
	private final BillingRepository billingRepository;
	private final AppointmentRepository appointmentRepository;
	private final UserRepository userRepository;

	// Create Billing
	public BillingResponse createBilling(BillingRequest request) {
		Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found"));

		Billing billing = Billing.builder()
				.amount(request.getAmount())
				.billingDate(request.getBillingDate())
				.appointment(appointment)
				.paymentStatus(request.getPaymentStatus())
				.build();

		return toDto(billingRepository.save(billing));
	}

	// Get All Billings
	public List<BillingResponse> getAllBillings() {
		return billingRepository.findAll().stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}
	// Get All Billings By Status
	public List<BillingResponse> getBillingsByStatus(PaymentStatus status) {
		List<Billing> billings = billingRepository.findByPaymentStatus(status);
		return billings.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	// Get Billing by ID
	public BillingResponse getBillingById(Long id) {
		Billing billing = billingRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Billing not found"));
		return toDto(billing);
	}

	public BillingResponse updatePaymentStatusByAppointmentId(Long appointmentId, PaymentStatus status) {
		Billing billing = billingRepository.findByAppointmentId(appointmentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Billing not found for this appointment"));

		billing.setPaymentStatus(status);
		return toDto(billingRepository.save(billing));
	}

	public BillingResponse getBillingByAppointmentId(Long appointmentId, String username) {

		Appointment appointment = appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Appointment not found"));

		// Fetch logged-in user (assumes username is email or unique identifier)
		Users user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"username not found"));

		// Ensure the logged-in user is the owner of the appointment
		if (!appointment.getPatient().getUser().getId().equals(user.getId())) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to access this billing info.");
		}

		Billing billing = billingRepository.findByAppointmentId(appointmentId).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Billing not found for this appointment"));

		return new BillingResponse(
				billing.getId(),
				billing.getAmount(),
				billing.getBillingDate(),
				billing.getAppointment().getId(),
				billing.getAppointment().getPatient().getName(),
				billing.getAppointment().getDoctor().getName(),
				billing.getPaymentStatus()
		);
	}
// ---------- MAPPER ----------
	private BillingResponse toDto(Billing b) {
		return BillingResponse.builder()
				.id(b.getId())
				.amount(b.getAmount())
				.billingDate(b.getBillingDate())
				.appointmentId(b.getAppointment().getId())
				.patientName(b.getAppointment().getPatient().getName())
				.doctorName(b.getAppointment().getDoctor().getName())
				.paymentStatus(b.getPaymentStatus())
				.build();
	}
}
