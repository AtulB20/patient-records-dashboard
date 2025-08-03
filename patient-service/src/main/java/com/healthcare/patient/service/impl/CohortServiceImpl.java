package com.healthcare.patient.service.impl;

import com.healthcare.patient.dto.CohortDTO;
import com.healthcare.patient.dto.PatientDTO;
import com.healthcare.patient.exception.ResourceNotFoundException;
import com.healthcare.patient.model.Cohort;
import com.healthcare.patient.model.CohortFilter;
import com.healthcare.patient.model.Patient;
import com.healthcare.patient.repository.CohortRepository;
import com.healthcare.patient.repository.PatientRepository;
import com.healthcare.patient.service.CohortService;

import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
public class CohortServiceImpl implements CohortService {
  
  @Autowired
  private CohortRepository cohortRepository;

  @Autowired
  private PatientRepository patientRepository;

  @Override
  @Transactional
  public Page<PatientDTO> getPatientsInCohort(Long cohortId, Pageable pageable) {
    log.debug("Fetching patients for cohort ID: {}", cohortId);

    // Get the cohort to access its criteria
    Cohort cohort = cohortRepository.findById(cohortId)
        .orElseThrow(() -> new ResourceNotFoundException("Cohort not found with id: " + cohortId));

    // Convert criteria to specification
    Specification<Patient> spec = createSpecificationFromCriteria(cohort.getCohortFilter());

    // Apply specification to filter patients
    return patientRepository.findAll(spec, pageable)
        .map(PatientDTO::fromEntity);
  }

  @Override
  @Transactional
  public CohortDTO createCohort(CohortDTO cohortDto, Long createdBy) {
    log.info("Creating new cohort: {}", cohortDto.getName());
    
    if (cohortRepository.existsByName(cohortDto.getName())) {
      throw new IllegalArgumentException("Cohort with name " + cohortDto.getName() + " already exists");
    }
    Cohort cohort = CohortDTO.toEntity(cohortDto, createdBy);
    
    Cohort savedCohort = cohortRepository.save(cohort);
    log.info("Created cohort with ID: {}", savedCohort.getId());
    
    return CohortDTO.from(savedCohort);
  }

  @Override
  @Transactional
  public CohortDTO updateCohort(Long id, CohortDTO cohortDto) {
    log.info("Updating cohort with ID: {}", id);
    
    Cohort existingCohort = cohortRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Cohort not found with id: " + id));
    
    // Check if name is being changed and if the new name already exists
    if (!existingCohort.getName().equals(cohortDto.getName()) && 
        cohortRepository.existsByName(cohortDto.getName())) {
      throw new IllegalArgumentException("Another cohort with name " + cohortDto.getName() + " already exists");
    }
    
    existingCohort.setName(cohortDto.getName());
    existingCohort.setDescription(cohortDto.getDescription());
    existingCohort.setCohortFilter(cohortDto.getCriteria());
    
    Cohort updatedCohort = cohortRepository.save(existingCohort);
    log.info("Updated cohort with ID: {}", id);
    
    return CohortDTO.from(updatedCohort);
  }

  @Override
  public void deleteCohort(Long id) {
    log.info("Deleting cohort with ID: {}", id);
    cohortRepository.deleteById(id);
    log.info("Deleted cohort with ID: {}", id);
  }

  @Override
  public CohortDTO getCohortById(Long id) {
    log.info("Fetching cohort with ID: {}", id);
    Cohort cohort = cohortRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Cohort not found with id: " + id));
    log.info("Fetched cohort with ID: {}", id);
    return CohortDTO.from(cohort);
  }

  @Override
  public Page<CohortDTO> getAllCohorts(Pageable pageable) {
    log.info("Fetching all cohorts");
    return cohortRepository.findAll(pageable).map(CohortDTO::from);
  }

  private Specification<Patient> createSpecificationFromCriteria(CohortFilter criteria) {
    return (root, query, criteriaBuilder) -> {
      var predicates = new ArrayList<Predicate>();
      
      if (criteria.getName() != null) {
        predicates.add(criteriaBuilder.like(root.get("name"), "%" + criteria.getName() + "%"));
      }
      
      if (criteria.getEmail() != null) {
        predicates.add(criteriaBuilder.like(root.get("email"), "%" + criteria.getEmail() + "%"));
      }
      
      if (criteria.getPhone() != null) {
        predicates.add(criteriaBuilder.like(root.get("phone"), "%" + criteria.getPhone() + "%"));
      }
      
      if (criteria.getDobFrom() != null) {
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dob"), criteria.getDobFrom()));
      }
      
      if (criteria.getDobTo() != null) {
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dob"), criteria.getDobTo()));
      }
      
      if (criteria.getGender() != null) {
        predicates.add(criteriaBuilder.equal(root.get("gender"), criteria.getGender()));
      }
      
      if (criteria.getStatuses() != null && !criteria.getStatuses().isEmpty()) {
        predicates.add(criteriaBuilder.in(root.get("status")).value(criteria.getStatuses()));
      }
      
      if (criteria.getDoctorId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("doctor"), criteria.getDoctorId()));
      }
      
      if (criteria.getNurseId() != null) {
        predicates.add(criteriaBuilder.equal(root.get("nurse"), criteria.getNurseId()));
      }
      
      if (criteria.getHasPendingAppointments() != null) {
        predicates.add(criteriaBuilder.equal(root.get("hasPendingAppointments"), criteria.getHasPendingAppointments()));
      }
      
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
    
  }
}
