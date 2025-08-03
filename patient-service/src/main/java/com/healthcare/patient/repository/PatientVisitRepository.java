package com.healthcare.patient.repository;

import com.healthcare.patient.model.Patient;
import com.healthcare.patient.model.PatientVisit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PatientVisitRepository extends JpaRepository<PatientVisit, Long> {
    Page<PatientVisit> findByPatientOrderByVisitDateDesc(Patient patient, Pageable pageable);
    Page<PatientVisit> findByPatientAndVisitDateBetween(
        Patient patient, 
        LocalDateTime startDate, 
        LocalDateTime endDate, 
        Pageable pageable
    );
}
