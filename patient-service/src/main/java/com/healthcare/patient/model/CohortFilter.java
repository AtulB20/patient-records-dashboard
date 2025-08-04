package com.healthcare.patient.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CohortFilter {
    private String name;          // Full name or partial match
    private String email;         // Exact email match
    private String phone;         // Partial phone number match
    private LocalDate dobFrom;   // Date of birth range (start)
    private LocalDate dobTo;     // Date of birth range (end)
    private String gender;        // "M", "F", "OTHER"
    private List<String> statuses; // e.g., ["ACTIVE", "INACTIVE"]
    private String doctorId;      // Filter by assigned doctor
    private String nurseId;       // Filter by assigned nurse
    private Boolean hasPendingAppointments; // Boolean filter

    // Pagination/Sorting fields (often included in filters)
    private Integer page = 0;
    private Integer size = 10;
    private String sortBy = "firstName"; // Default sort field
    private String sortDirection = "ASC"; // "ASC" or "DESC"
}
