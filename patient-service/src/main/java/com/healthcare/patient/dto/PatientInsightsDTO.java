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
    
    /**
     * Total number of visits for the patient
     */
    private Integer totalVisits;
    
    /**
     * Number of visits in the last 12 months
     */
    private Integer visitsLastYear;
    
    /**
     * Date of the most recent visit
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisitDate;
    
    /**
     * List of unique diagnosis codes from all visits
     */
    private List<String> diagnoses;
    
    /**
     * Names of doctors who have seen this patient
     */
    private List<String> treatingDoctors;
    
    /**
     * Number of active prescriptions
     */
    private Integer activePrescriptions;
    
    /**
     * Number of pending appointments
     */
    private Integer pendingAppointments;
    
    /**
     * Converts a Patient entity to a PatientInsightsDTO with basic information.
     * Note: The insight fields will need to be populated separately by the service layer.
     * 
     * @param patient The patient entity to convert
     * @return A new PatientInsightsDTO instance with basic patient information
     */
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
