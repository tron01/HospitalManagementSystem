package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.DTO.BillingRequest;
import com.Abhijith.HospitalManagementSystem.DTO.BillingResponse;
import com.Abhijith.HospitalManagementSystem.Model.Appointment;
import com.Abhijith.HospitalManagementSystem.Model.Billing;
import com.Abhijith.HospitalManagementSystem.Model.PaymentStatus;
import com.Abhijith.HospitalManagementSystem.Repository.AppointmentRepository;
import com.Abhijith.HospitalManagementSystem.Repository.BillingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillingService {
	private final BillingRepository billingRepository;
	private final AppointmentRepository appointmentRepository;

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

	// Get Billing by ID
	public BillingResponse getBillingById(Long id) {
		Billing billing = billingRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Billing not found"));
		return toDto(billing);
	}

	// Update Payment Status
	public BillingResponse updatePaymentStatus(Long id, PaymentStatus status) {
		Billing billing = billingRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Billing not found"));

		billing.setPaymentStatus(status);
		return toDto(billingRepository.save(billing));
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
