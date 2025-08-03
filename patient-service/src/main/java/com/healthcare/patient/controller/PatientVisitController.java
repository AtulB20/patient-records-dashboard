/*
package com.healthcare.patient.controller;

import com.healthcare.patient.dto.PatientVisitDto;
import com.healthcare.patient.service.PatientVisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients/{patientId}/visits")
@RequiredArgsConstructor
public class PatientVisitController {

    private final PatientVisitService visitService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_NURSE')")
    public Page<PatientVisitDto> getPatientVisits(
            @PathVariable Long patientId,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @PageableDefault(size = 20) Pageable pageable) {
        
        if (startDate != null && endDate != null) {
            return visitService.findByPatientIdAndDateRange(patientId, startDate, endDate, pageable);
        }
        return visitService.findByPatientId(patientId, pageable);
    }

    @GetMapping("/{visitId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_NURSE')")
    public ResponseEntity<PatientVisitDto> getPatientVisitById(
            @PathVariable Long patientId,
            @PathVariable Long visitId) {
        
        return visitService.findById(visitId)
                .filter(visit -> visit.getPatientId().equals(patientId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    public PatientVisitDto createPatientVisit(
            @PathVariable Long patientId,
            @Valid @RequestBody PatientVisitDto visitDto) {
        
        visitDto.setPatientId(patientId);
        return visitService.save(visitDto);
    }

    @PutMapping("/{visitId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    public ResponseEntity<PatientVisitDto> updatePatientVisit(
            @PathVariable Long patientId,
            @PathVariable Long visitId,
            @Valid @RequestBody PatientVisitDto visitDto) {
        
        return visitService.findById(visitId)
                .filter(visit -> visit.getPatientId().equals(patientId))
                .map(visit -> {
                    visitDto.setId(visitId);
                    visitDto.setPatientId(patientId);
                    return ResponseEntity.ok(visitService.update(visitId, visitDto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{visitId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatientVisit(
            @PathVariable Long patientId,
            @PathVariable Long visitId) {
        
        visitService.findById(visitId)
                .filter(visit -> visit.getPatientId().equals(patientId))
                .ifPresent(visit -> visitService.delete(visitId));
    }
}
*/
