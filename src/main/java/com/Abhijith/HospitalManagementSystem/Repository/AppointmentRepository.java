package com.Abhijith.HospitalManagementSystem.Repository;

import com.Abhijith.HospitalManagementSystem.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
