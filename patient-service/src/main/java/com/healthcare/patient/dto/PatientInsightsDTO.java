package com.healthcare.patient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.healthcare.patient.model.Patient;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for patient data with additional insights like visit history and diagnoses.
 * Extends the base PatientDTO with additional computed fields.
 */
@Getter
@Setter
@SuperBuilder
public class PatientInsightsDTO extends PatientDTO {
    
    private Integer totalVisits;
    private Integer visitsLastYear;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisitDate;
    
    private List<String> diagnoses;
    private List<String> treatingDoctors;
    private Integer activePrescriptions;
    private Integer pendingAppointments;
    
    public static PatientInsightsDTO fromPatient(Patient patient) {
        if (patient == null) {
            return null;
        }
        
        return PatientInsightsDTO.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .address(patient.getAddress())
                .phoneNumber(patient.getPhoneNumber())
                .email(patient.getEmail())
                .bloodType(patient.getBloodType())
                .heightCm(patient.getHeightCm())
                .weightKg(patient.getWeightKg())
                .emergencyContactName(patient.getEmergencyContactName())
                .emergencyContactPhone(patient.getEmergencyContactPhone())
                .totalVisits(0)
                .visitsLastYear(0)
                .activePrescriptions(0)
                .pendingAppointments(0)
                .build();
    }
}
