// package com.healthcare.patient.service.impl;

// import com.healthcare.patient.dto.PatientMedicationDto;
// import com.healthcare.patient.exception.ResourceNotFoundException;
// import com.healthcare.patient.model.Patient;
// import com.healthcare.patient.model.PatientMedication;
// import com.healthcare.patient.model.PatientVisit;
// import com.healthcare.patient.model.User;
// import com.healthcare.patient.repository.PatientMedicationRepository;
// import com.healthcare.patient.repository.PatientRepository;
// import com.healthcare.patient.repository.PatientVisitRepository;
// import com.healthcare.patient.repository.UserRepository;
// import com.healthcare.patient.service.PatientMedicationService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.oauth2.jwt.Jwt;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDate;
// import java.util.List;
// import java.util.Optional;

// @Service
// @RequiredArgsConstructor
// public class PatientMedicationServiceImpl implements PatientMedicationService {

//     private final PatientMedicationRepository medicationRepository;
//     private final PatientRepository patientRepository;
//     private final PatientVisitRepository visitRepository;
//     private final UserRepository userRepository;

//     @Override
//     @Transactional(readOnly = true)
//     public List<PatientMedicationDto> findAll() {
//         return medicationRepository.findAll().stream()
//                 .map(PatientMedicationDto::fromEntity)
//                 .toList();
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Page<PatientMedicationDto> findAll(Pageable pageable) {
//         return medicationRepository.findAll(pageable)
//                 .map(PatientMedicationDto::fromEntity);
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Optional<PatientMedicationDto> findById(Long id) {
//         return medicationRepository.findById(id)
//                 .map(PatientMedicationDto::fromEntity);
//     }

//     @Override
//     @Transactional
//     public PatientMedicationDto save(PatientMedicationDto medicationDto) {
//         PatientMedication medication = new PatientMedication();
//         updateMedicationFromDto(medication, medicationDto);
//         return PatientMedicationDto.fromEntity(medicationRepository.save(medication));
//     }

//     @Override
//     @Transactional
//     public PatientMedicationDto update(Long id, PatientMedicationDto medicationDto) {
//         return medicationRepository.findById(id)
//                 .map(existingMedication -> {
//                     updateMedicationFromDto(existingMedication, medicationDto);
//                     return PatientMedicationDto.fromEntity(medicationRepository.save(existingMedication));
//                 })
//                 .orElseThrow(() -> new ResourceNotFoundException("Medication not found with id: " + id));
//     }

//     @Override
//     @Transactional
//     public void delete(Long id) {
//         if (medicationRepository.existsById(id)) {
//             medicationRepository.deleteById(id);
//         } else {
//             throw new ResourceNotFoundException("Medication not found with id: " + id);
//         }
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public boolean exists(Long id) {
//         return medicationRepository.existsById(id);
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Page<PatientMedicationDto> findByPatientId(Long patientId, Pageable pageable) {
//         Patient patient = patientRepository.findById(patientId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
//         return medicationRepository.findByPatient(patient, pageable)
//                 .map(PatientMedicationDto::fromEntity);
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Page<PatientMedicationDto> findCurrentMedicationsByPatientId(Long patientId, Pageable pageable) {
//         Patient patient = patientRepository.findById(patientId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        
//         LocalDate today = LocalDate.now();
//         return medicationRepository.findByPatientAndEndDateIsNullOrEndDateAfter(patient, today, pageable)
//                 .map(PatientMedicationDto::fromEntity);
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Page<PatientMedicationDto> findByPatientIdAndMedicationName(Long patientId, String medicationName, Pageable pageable) {
//         Patient patient = patientRepository.findById(patientId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
        
//         return medicationRepository.findByPatientAndMedicationNameContainingIgnoreCase(patient, medicationName, pageable)
//                 .map(PatientMedicationDto::fromEntity);
//     }

//     private void updateMedicationFromDto(PatientMedication medication, PatientMedicationDto dto) {
//         Patient patient = patientRepository.findById(dto.getPatientId())
//                 .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));
        
//         User prescribedBy = userRepository.findById(dto.getPrescribedById())
//                 .orElseThrow(() -> new ResourceNotFoundException("Prescriber not found with id: " + dto.getPrescribedById()));
        
//         PatientVisit visit = null;
//         if (dto.getVisitId() != null) {
//             visit = visitRepository.findById(dto.getVisitId())
//                     .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + dto.getVisitId()));
//         }

//         medication.setPatient(patient);
//         medication.setVisit(visit);
//         medication.setMedicationName(dto.getMedicationName());
//         medication.setDosage(dto.getDosage());
//         medication.setFrequency(dto.getFrequency());
//         medication.setStartDate(dto.getStartDate());
//         medication.setEndDate(dto.getEndDate());
//         medication.setInstructions(dto.getInstructions());
//         medication.setPrescribedBy(prescribedBy);
//     }

//     private Long getCurrentUserId() {
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//         if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
//             Jwt jwt = (Jwt) authentication.getPrincipal();
//             return Long.parseLong(jwt.getSubject());
//         }
//         throw new IllegalStateException("Unable to get current user ID");
//     }
// }
