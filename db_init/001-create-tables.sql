-- Drop tables in reverse order of dependency
DROP TABLE IF EXISTS PATIENT;
DROP TABLE IF EXISTS USER_ROLE;
DROP TABLE IF EXISTS HC_ROLE;
DROP TABLE IF EXISTS HC_USER;

-- Users table for authentication
CREATE TABLE IF NOT EXISTS HC_USER (
    id serial PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    enabled BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Role table to hold predefined user roles
CREATE TABLE IF NOT EXISTS HC_ROLE (
    id serial PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

-- User-Role mapping
CREATE TABLE IF NOT EXISTS USER_ROLE (
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES HC_USER (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES HC_ROLE (id) ON DELETE CASCADE
);

-- Patient table
CREATE TABLE IF NOT EXISTS PATIENT (
    id bigserial PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(20) NOT NULL,
    address TEXT,
    phone_number VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    blood_type VARCHAR(10),
    height_cm DECIMAL(5,2),
    weight_kg DECIMAL(5,2),
    emergency_contact_name VARCHAR(200),
    emergency_contact_phone VARCHAR(20),
    created_by BIGINT NOT NULL REFERENCES HC_USER(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ACTIVE'
);

-- Patient Visit Table
CREATE TABLE IF NOT EXISTS PATIENT_VISIT (
    id BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL REFERENCES patient(id) ON DELETE CASCADE,
    visit_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    doctor_id BIGINT NOT NULL REFERENCES hc_user(id),
    diagnosis TEXT,
    treatment TEXT,
    visit_notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Cohort table
CREATE TABLE IF NOT EXISTS PATIENT_COHORT (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    cohort_filter JSON,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_system BOOLEAN DEFAULT FALSE,
    UNIQUE KEY uk_cohort_name (name)
);

-- Create indexes for PATIENT table
CREATE INDEX IF NOT EXISTS idx_patient_name ON PATIENT (last_name, first_name);
CREATE INDEX IF NOT EXISTS idx_patient_dob ON PATIENT (date_of_birth);
CREATE INDEX IF NOT EXISTS idx_patient_status ON PATIENT (status);
CREATE INDEX IF NOT EXISTS idx_patient_email ON PATIENT (email);
CREATE INDEX IF NOT EXISTS idx_visit_patient ON PATIENT_VISIT(patient_id);
CREATE INDEX IF NOT EXISTS idx_visit_date ON PATIENT_VISIT(visit_date);
