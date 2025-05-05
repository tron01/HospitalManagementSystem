package com.Abhijith.HospitalManagementSystem.Repository;

import com.Abhijith.HospitalManagementSystem.Model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByName(String name);
    Optional<Patient> findByUserId(Long userId);
    Optional<Patient> findByUserUsername(String username);

}
