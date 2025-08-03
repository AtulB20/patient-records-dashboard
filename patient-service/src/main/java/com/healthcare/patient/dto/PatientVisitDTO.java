package com.healthcare.patient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.healthcare.patient.model.PatientVisit;
import com.healthcare.patient.model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientVisitDTO {
    private Long id;
    
    @NotNull(message = "Patient ID is required")
    private Long patientId;
    
    @NotNull(message = "Visit date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime visitDate;
    
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;
    private String doctorName;
    
    @Size(max = 1000, message = "Diagnosis must be less than 1000 characters")
    private String diagnosis;
    
    @Size(max = 2000, message = "Treatment must be less than 2000 characters")
    private String treatment;
    
    @Size(max = 2000, message = "Notes must be less than 2000 characters")
    private String notes;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public static PatientVisitDTO fromEntity(PatientVisit visit) {
        if (visit == null) {
            return null;
        }
        
        User doctor = visit.getDoctor();
        String doctorFullName = doctor != null ? 
            String.format("%s %s", 
                doctor.getFirstName() != null ? doctor.getFirstName() : "", 
                doctor.getLastName() != null ? doctor.getLastName() : ""
            ).trim() : "";
        
        return PatientVisitDTO.builder()
                .id(visit.getId())
                .patientId(visit.getPatient() != null ? visit.getPatient().getId() : null)
                .visitDate(visit.getVisitDate())
                .doctorId(doctor != null ? doctor.getId() : null)
                .doctorName(doctorFullName)
                .diagnosis(visit.getDiagnosis())
                .treatment(visit.getTreatment())
                .notes(visit.getNotes())
                .createdAt(visit.getCreatedAt())
                .updatedAt(visit.getUpdatedAt())
                .build();
    }
}
