package com.Abhijith.HospitalManagementSystem.Repository;

import com.Abhijith.HospitalManagementSystem.Model.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
}
