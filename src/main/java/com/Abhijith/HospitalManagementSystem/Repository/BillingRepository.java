package com.Abhijith.HospitalManagementSystem.Repository;

import com.Abhijith.HospitalManagementSystem.Model.Billing;
import com.Abhijith.HospitalManagementSystem.Model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
	Optional<Billing> findByAppointmentId(Long appointmentId);
	List<Billing> findByPaymentStatus(PaymentStatus status);
}
