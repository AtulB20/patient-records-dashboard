package com.healthcare.patient.service;

import com.healthcare.patient.dto.PatientVisitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PatientVisitService {
    Page<PatientVisitDTO> findByPatientId(Long patientId, Pageable pageable);
    Page<PatientVisitDTO> findByPatientIdAndDateRange(Long patientId, String startDate, String endDate, Pageable pageable);
}
