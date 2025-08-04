package com.healthcare.patient.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.healthcare.patient.model.Cohort;
import com.healthcare.patient.model.CohortFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CohortDTO {
    private Long id;
    private String name;
    private String description;
    private CohortFilter criteria;
    private Long createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean system;
    private Long patientCount; // Will be populated when needed
    
    public static CohortDTO from(Cohort cohort) {
        if (cohort == null) {
            return null;
        }
        
        return CohortDTO.builder()
                .id(cohort.getId())
                .name(cohort.getName())
                .description(cohort.getDescription())
                .criteria(cohort.getCohortFilter())
                .createdBy(cohort.getCreatedBy())
                .createdAt(cohort.getCreatedAt())
                .updatedAt(cohort.getUpdatedAt())
                .system(cohort.isSystem())
                .build();
    }

    public static Cohort toEntity(CohortDTO cohortDto, Long createdBy) {
        if (cohortDto == null) {
            return null;
        }
        
        return Cohort.builder()
            .name(cohortDto.getName())
            .description(cohortDto.getDescription())
            .cohortFilter(cohortDto.getCriteria())
            .createdBy(createdBy)
            .system(false)
            .build();
    }
}
