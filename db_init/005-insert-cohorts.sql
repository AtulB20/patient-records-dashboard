-- Sample data for patient_cohort table
INSERT INTO patient_cohort (name, description, cohort_filter, created_by, is_system) VALUES
-- Cohort 1: Active patients with pending appointments
('Active with Pending Appointments', 'Patients who have pending appointments', 
 '{"statuses": ["ACTIVE"], "hasPendingAppointments": true}', 1, false),

-- Cohort 2: Senior patients (65+ years old)
('Senior Patients', 'Patients aged 65 and above', 
 '{"dobTo": "1958-01-01", "sortBy": "dateOfBirth", "sortDirection": "ASC"}', 1, false),

-- Cohort 3: High-risk patients
('High Risk Patients', 'Patients with high-risk conditions', 
 '{"statuses": ["HIGH_RISK"], "sortBy": "lastName", "sortDirection": "ASC"}', 2, false),

-- Cohort 4: Patients with recent visits
('Recent Visits - Last 30 Days', 'Patients who visited in the last 30 days', 
 '{"lastVisitFrom": "2025-07-04", "sortBy": "lastVisitDate", "sortDirection": "DESC"}', 1, false),

-- System cohort: All active patients (can be used as a base for other cohorts)
('All Active Patients', 'System cohort - all active patients', 
 '{"statuses": ["ACTIVE"]}', 1, true);