-- Insert default admin user (password: admin123)
INSERT INTO HC_USER (username, password, email, first_name, last_name, enabled, created_at, updated_at)
VALUES ('admin', '$2a$10$5qIV/EeK3Ssyxp7FYvG7fOMbsJWaF5p7hNREoax/uHQwARojiYG/G', 'admin@healthcare.com', 'System', 'Administrator', true, current_timestamp, current_timestamp),
-- Doctor
('dr.kapoor', '$2a$10$5qIV/EeK3Ssyxp7FYvG7fOMbsJWaF5p7hNREoax/uHQwARojiYG/G', 'dr.kapoor@healthcare.com', 'Rahul', 'Kapoor', true, current_timestamp, current_timestamp),
-- Nurse
('nurse.priya', '$2a$10$5qIV/EeK3Ssyxp7FYvG7fOMbsJWaF5p7hNREoax/uHQwARojiYG/G', 'priya.n@healthcare.com', 'Priya', 'Nair', true, current_timestamp, current_timestamp),
-- Staff
('staff.raj', '$2a$10$5qIV/EeK3Ssyxp7FYvG7fOMbsJWaF5p7hNREoax/uHQwARojiYG/G', 'raj.m@healthcare.com', 'Raj', 'Mehta', true, current_timestamp, current_timestamp),
-- Doctor
('dr.desai', '$2a$10$5qIV/EeK3Ssyxp7FYvG7fOMbsJWaF5p7hNREoax/uHQwARojiYG/G', 'anita.desai@healthcare.com', 'Anita', 'Desai', true, current_timestamp, current_timestamp);
