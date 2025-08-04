-- Insert sample patient visits
INSERT INTO PATIENT_VISIT (patient_id, doctor_id, visit_date, diagnosis, treatment, visit_notes, status, created_at, updated_at)
VALUES 
-- Patient 1 visits
(1, 2, '2025-08-01 09:30:00', 'Hypertension', 'Prescribed Lisinopril 10mg daily', 'Patient reports feeling better with current medication', 'COMPLETED', '2025-08-01 09:30:00', '2025-08-01 10:15:00'),
(1, 2, '2025-09-01 10:00:00', 'Hypertension Follow-up', 'Continue current medication, schedule next visit in 2 months', 'Blood pressure well controlled', 'PENDING', '2025-09-01 10:00:00', '2025-09-01 10:00:00'),

-- Patient 2 visits
(2, 5, '2025-07-15 14:30:00', 'Type 2 Diabetes', 'Prescribed Metformin 500mg twice daily', 'Newly diagnosed, needs dietary counseling', 'COMPLETED', '2025-07-15 14:30:00', '2025-07-15 15:30:00'),
(2, 5, '2025-08-15 15:00:00', 'Diabetes Follow-up', 'Adjusted Metformin to 850mg twice daily', 'Blood sugar levels improving but still high', 'COMPLETED', '2025-08-15 15:00:00', '2025-08-15 16:00:00'),
(2, 5, '2025-09-20 16:00:00', 'Diabetes Checkup', 'Blood work required before visit', 'Routine checkup', 'PENDING', '2025-09-20 16:00:00', '2025-09-20 16:00:00'),

-- Patient 3 visits
(3, 5, '2025-08-10 11:00:00', 'Annual Physical', 'All tests normal, advised regular exercise', 'Patient in good health', 'COMPLETED', '2025-08-10 11:00:00', '2025-08-10 12:00:00'),

-- Patient 4 visits
(4, 2, '2025-08-05 13:30:00', 'Migraine', 'Prescribed Sumatriptan 50mg as needed', 'Patient reports 3-4 migraines per month', 'COMPLETED', '2025-08-05 13:30:00', '2025-08-05 14:15:00'),
(4, 2, '2025-08-25 14:00:00', 'Migraine Follow-up', 'Prescribed Propranolol 20mg daily for prevention', 'Patient reports 2 migraines since last visit', 'PENDING', '2025-08-25 14:00:00', '2025-08-25 14:00:00'),

-- Patient 5 visits
(5, 5, '2025-08-12 10:30:00', 'Allergic Rhinitis', 'Prescribed Fluticasone nasal spray', 'Seasonal allergies, advised allergen avoidance', 'COMPLETED', '2025-08-12 10:30:00', '2025-08-12 11:00:00'),
(5, 2, '2025-09-15 09:00:00', 'Allergy Follow-up', 'Assess response to Fluticasone', 'Routine follow-up', 'PENDING', '2025-09-15 09:00:00', '2025-09-15 09:00:00');