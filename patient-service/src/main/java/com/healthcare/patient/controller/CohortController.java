package com.healthcare.patient.controller;

import com.healthcare.patient.dto.CohortDTO;
import com.healthcare.patient.dto.PatientDTO;
import com.healthcare.patient.service.CohortService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cohorts")
@RequiredArgsConstructor
public class CohortController {

  private final CohortService cohortService;

  /**
   * Get patients that match the criteria defined in the cohort
   * @param id Cohort ID
   * @param pageable Pagination information
   * @return Page of patients matching the cohort criteria
   */
  @GetMapping("/{id}/patients")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_NURSE')")
  public ResponseEntity<Page<PatientDTO>> getPatientsInCohort(
      @PathVariable Long id,
      @PageableDefault(size = 20) Pageable pageable) {

    return ResponseEntity.ok(cohortService.getPatientsInCohort(id, pageable));
  }
  
  /**
   * Create a new cohort
   * @param cohortDto Cohort information
   * @return Created cohort
   */
  @PostMapping
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_NURSE')")
  public ResponseEntity<CohortDTO> createCohort(@Valid @RequestBody CohortDTO cohortDto) {
    return ResponseEntity.ok(cohortService.createCohort(cohortDto));
  }

  /**
   * Update an existing cohort
   * @param id Cohort ID
   * @param cohortDto Cohort information
   * @return Updated cohort
   */
  @PutMapping("/{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_NURSE')")
  public ResponseEntity<CohortDTO> updateCohort(
      @PathVariable Long id,
      @Valid @RequestBody CohortDTO cohortDto) {
    return ResponseEntity.ok(cohortService.updateCohort(id, cohortDto));
  }

  /**
   * Delete a cohort
   * @param id Cohort ID
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_NURSE')")
  public ResponseEntity<Void> deleteCohort(@PathVariable Long id) {
    cohortService.deleteCohort(id);
    return ResponseEntity.ok().build();
  }

  /**
   * Get a cohort by ID
   * @param id Cohort ID
   * @return Cohort information
   */
  @GetMapping("/{id}")
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_NURSE')")
  public ResponseEntity<CohortDTO> getCohortById(@PathVariable Long id) {
    return ResponseEntity.ok(cohortService.getCohortById(id));
  } 

  /**
   * Get all cohorts
   * @return List of cohorts
   */
  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_NURSE')")
  public ResponseEntity<Page<CohortDTO>> getAllCohorts(@PageableDefault(size = 20) Pageable pageable) {
    return ResponseEntity.ok(cohortService.getAllCohorts(pageable));
  }
  
}