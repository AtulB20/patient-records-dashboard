package com.healthcare.patient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.healthcare.patient.model.Patient;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must be less than 100 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must be less than 100 characters")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Gender is required")
    private String gender;

    private String address;

    @Pattern(regexp = "^$|^\\+?[0-9. ()-]{7,}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    private String email;

    private String bloodType;
    private BigDecimal heightCm;
    private BigDecimal weightKg;
    private String emergencyContactName;
    private String emergencyContactPhone;

    public static PatientDTO fromEntity(Patient patient) {
        if (patient == null) {
            return null;
        }
        
        return PatientDTO.builder()
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
                .build();
    }

    public static Patient toEntity(PatientDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Patient.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .dateOfBirth(dto.getDateOfBirth())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .bloodType(dto.getBloodType())
                .heightCm(dto.getHeightCm())
                .weightKg(dto.getWeightKg())
                .emergencyContactName(dto.getEmergencyContactName())
                .emergencyContactPhone(dto.getEmergencyContactPhone())
                .build();
    }
}
