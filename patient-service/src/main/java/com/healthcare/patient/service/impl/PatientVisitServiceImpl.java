// package com.healthcare.patient.service.impl;

// import com.healthcare.patient.dto.PatientVisitDTO;
// import com.healthcare.patient.exception.ResourceNotFoundException;
// import com.healthcare.patient.model.Patient;
// import com.healthcare.patient.model.PatientVisit;
// import com.healthcare.patient.model.User;
// import com.healthcare.patient.repository.PatientRepository;
// import com.healthcare.patient.repository.PatientVisitRepository;
// import com.healthcare.patient.repository.UserRepository;
// import com.healthcare.patient.service.PatientVisitService;
// import lombok.RequiredArgsConstructor;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.oauth2.jwt.Jwt;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

// @Service
// @RequiredArgsConstructor
// public class PatientVisitServiceImpl implements PatientVisitService {

//     private final PatientVisitRepository visitRepository;
//     private final PatientRepository patientRepository;
//     private final UserRepository userRepository;
//     private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE_TIME;

//     @Override
//     @Transactional(readOnly = true)
//     public List<PatientVisitDTO> findAll() {
//         return visitRepository.findAll().stream()
//                 .map(PatientVisitDTO::fromEntity)
//                 .toList();
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Page<PatientVisitDTO> findAll(Pageable pageable) {
//         return visitRepository.findAll(pageable)
//                 .map(PatientVisitDTO::fromEntity);
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Optional<PatientVisitDTO> findById(Long id) {
//         return visitRepository.findById(id)
//                 .map(PatientVisitDTO::fromEntity);
//     }

//     @Override
//     @Transactional
//     public PatientVisitDTO save(PatientVisitDTO visitDto) {
//         PatientVisit visit = new PatientVisit();
//         updateVisitFromDTO(visit, visitDto);
//         return PatientVisitDTO.fromEntity(visitRepository.save(visit));
//     }

//     @Override
//     @Transactional
//     public PatientVisitDTO update(Long id, PatientVisitDTO visitDto) {
//         return visitRepository.findById(id)
//                 .map(existingVisit -> {
//                     updateVisitFromDTO(existingVisit, visitDto);
//                     return PatientVisitDTO.fromEntity(visitRepository.save(existingVisit));
//                 })
//                 .orElseThrow(() -> new ResourceNotFoundException("Visit not found with id: " + id));
//     }

//     @Override
//     @Transactional
//     public void delete(Long id) {
//         if (visitRepository.existsById(id)) {
//             visitRepository.deleteById(id);
//         } else {
//             throw new ResourceNotFoundException("Visit not found with id: " + id);
//         }
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public boolean exists(Long id) {
//         return visitRepository.existsById(id);
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Page<PatientVisitDTO> findByPatientId(Long patientId, Pageable pageable) {
//         Patient patient = patientRepository.findById(patientId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
//         return visitRepository.findByPatientOrderByVisitDateDesc(patient, pageable)
//                 .map(PatientVisitDTO::fromEntity);
//     }

//     @Override
//     @Transactional(readOnly = true)
//     public Page<PatientVisitDTO> findByPatientIdAndDateRange(Long patientId, String startDateStr, String endDateStr, Pageable pageable) {
//         Patient patient = patientRepository.findById(patientId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));

//         LocalDateTime startDate = parseDateTime(startDateStr);
//         LocalDateTime endDate = parseDateTime(endDateStr);

//         return visitRepository.findByPatientAndVisitDateBetween(patient, startDate, endDate, pageable)
//                 .map(PatientVisitDTO::fromEntity);
//     }

//     private void updateVisitFromDTO(PatientVisit visit, PatientVisitDTO dto) {
//         Patient patient = patientRepository.findById(dto.getPatientId())
//                 .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + dto.getPatientId()));
        
//         User doctor = userRepository.findById(dto.getDoctorId())
//                 .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + dto.getDoctorId()));

//         visit.setPatient(patient);
//         visit.setDoctor(doctor);
//         visit.setVisitDate(dto.getVisitDate());
//         visit.setDiagnosis(dto.getDiagnosis());
//         visit.setTreatment(dto.getTreatment());
//         visit.setNotes(dto.getNotes());
//     }

//     private LocalDateTime parseDateTime(String dateTimeStr) {
//         if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
//             return null;
//         }
//         try {
//             return LocalDateTime.parse(dateTimeStr, DATE_FORMAT);
//         } catch (Exception e) {
//             throw new IllegalArgumentException("Invalid date format. Expected ISO-8601 format (e.g., 2023-01-01T10:00:00)");
//         }
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
