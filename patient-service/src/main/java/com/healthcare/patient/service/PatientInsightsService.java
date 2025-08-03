package com.healthcare.patient.service;

import com.healthcare.patient.dto.PatientInsightsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientInsightsService {
    
    /**
     * Get detailed insights for a specific patient
     * @param patientId The ID of the patient
     * @return PatientInsightsDTO with all available insights
     */
    PatientInsightsDTO getPatientInsights(Long patientId);
    
    /**
     * Get insights for all patients with pagination
     * @param pageable Pagination information
     * @return Page of PatientInsightsDTO
     */
    Page<PatientInsightsDTO> getAllPatientsInsights(Pageable pageable);
    
    /**
     * Get insights for patients in a specific cohort
     * @param cohortId The ID of the cohort
     * @param pageable Pagination information
     * @return Page of PatientInsightsDTO for patients in the cohort
     */
    Page<PatientInsightsDTO> getCohortPatientsInsights(Long cohortId, Pageable pageable);
}
