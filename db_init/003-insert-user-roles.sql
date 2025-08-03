-- Insert default roles
INSERT INTO HC_ROLE (name, description) VALUES
    ('ROLE_ADMIN', 'Administrator with full access'),
    ('ROLE_DOCTOR', 'Medical doctor role'),
    ('ROLE_NURSE', 'Nursing staff role'),
    ('ROLE_STAFF', 'General staff role');

-- Assign admin role to admin user
INSERT INTO USER_ROLE (user_id, role_id)
SELECT u.id, r.id 
FROM HC_USER u
CROSS JOIN HC_ROLE r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';

-- Assign roles to healthcare workers
-- Dr. Rahul Kapoor - Doctor
INSERT INTO USER_ROLE (user_id, role_id)
SELECT u.id, r.id 
FROM HC_USER u
CROSS JOIN HC_ROLE r
WHERE u.username = 'dr.kapoor' AND r.name = 'ROLE_DOCTOR';

-- Nurse Priya - Nurse
INSERT INTO USER_ROLE (user_id, role_id)
SELECT u.id, r.id 
FROM HC_USER u
CROSS JOIN HC_ROLE r
WHERE u.username = 'nurse.priya' AND r.name = 'ROLE_NURSE';

-- Staff Raj - Staff
INSERT INTO USER_ROLE (user_id, role_id)
SELECT u.id, r.id 
FROM HC_USER u
CROSS JOIN HC_ROLE r
WHERE u.username = 'staff.raj' AND r.name = 'ROLE_STAFF';

-- Dr. Anita Desai - Doctor + Admin
INSERT INTO USER_ROLE (user_id, role_id)
SELECT u.id, r.id 
FROM HC_USER u
CROSS JOIN HC_ROLE r
WHERE u.username = 'dr.desai' AND r.name IN ('ROLE_DOCTOR', 'ROLE_ADMIN');
