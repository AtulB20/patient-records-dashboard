package com.healthcare.patient.repository;

import com.healthcare.patient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
    List<Patient> findByLastNameContainingIgnoreCase(String lastName);
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
}
