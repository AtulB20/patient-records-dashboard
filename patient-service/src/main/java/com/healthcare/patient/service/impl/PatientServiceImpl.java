package com.healthcare.patient.service.impl;

import com.healthcare.patient.dto.PatientDTO;
import com.healthcare.patient.exception.ResourceNotFoundException;
import com.healthcare.patient.model.CohortFilter;
import com.healthcare.patient.model.Patient;
import com.healthcare.patient.model.PatientStatus;
import com.healthcare.patient.repository.PatientRepository;
import com.healthcare.patient.service.PatientService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientDTO getPatientById(Long id) {
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) {
            throw new ResourceNotFoundException("Patient not found with id: " + id);
        }
        return PatientDTO.fromEntity(patient.get());
    }

    @Override
    public Page<PatientDTO> filterPatients(CohortFilter filter) {
        Specification<Patient> spec = buildSpecification(filter);
        Pageable pageable = buildPageable(filter);
        return patientRepository.findAll(spec, pageable).map(PatientDTO::fromEntity);
    }

    @Override
    @Transactional
    public PatientDTO create(PatientDTO request) {
        if (patientRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        Patient patient = PatientDTO.toEntity(request);
        Patient savedPatient = patientRepository.save(patient);

        return PatientDTO.fromEntity(savedPatient);
    }

    @Override
    @Transactional
    public PatientDTO update(Long id, PatientDTO patientDto) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

        if (patientDto.getFirstName() != null) {
            patient.setFirstName(patientDto.getFirstName());
        }
        if (patientDto.getLastName() != null) {
            patient.setLastName(patientDto.getLastName());
        }
        if (patientDto.getDateOfBirth() != null) {
            patient.setDateOfBirth(patientDto.getDateOfBirth());
        }
        if (patientDto.getGender() != null) {
            patient.setGender(patientDto.getGender());
        }
        
        Patient updatedPatient = patientRepository.save(patient);
        return PatientDTO.fromEntity(updatedPatient);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Patient patient = patientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
        patient.setStatus(PatientStatus.INACTIVE); // Soft delete
        patientRepository.save(patient);
    }

    private Specification<Patient> buildSpecification(CohortFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if (filter.getName() != null) {
                predicates.add(cb.like(root.get("name"), "%" + filter.getName() + "%"));
            }
            if (filter.getDobFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("dob"), filter.getDobFrom()));
            }
            
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Pageable buildPageable(CohortFilter filter) {
        Sort.Direction direction = Sort.Direction.fromString(filter.getSortDirection());
        Sort sort = Sort.by(direction, filter.getSortBy());
        return PageRequest.of(filter.getPage(), filter.getSize(), sort);
    }

}
