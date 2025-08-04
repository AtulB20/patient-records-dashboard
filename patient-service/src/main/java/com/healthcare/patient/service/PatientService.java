package com.healthcare.patient.service;

import com.healthcare.patient.dto.PatientDTO;
import com.healthcare.patient.model.CohortFilter;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface PatientService extends PatientSpecification {
    PatientDTO getPatientById(Long id);
    Page<PatientDTO> filterPatients(CohortFilter filter);
    PatientDTO create(@Valid PatientDTO patientDto);
    PatientDTO update(Long id, @Valid PatientDTO patientDto);
    void delete(Long id);
}
