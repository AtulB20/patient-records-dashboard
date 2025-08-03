package com.healthcare.patient.service;

import com.healthcare.patient.dto.CohortDTO;
import com.healthcare.patient.dto.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CohortService {
  CohortDTO createCohort(CohortDTO cohortDto, Long createdBy);

  CohortDTO updateCohort(Long id, CohortDTO cohortDto);

  void deleteCohort(Long id);

  CohortDTO getCohortById(Long id);

  Page<CohortDTO> getAllCohorts(Pageable pageable);

  Page<PatientDTO> getPatientsInCohort(Long cohortId, Pageable pageable);
}
