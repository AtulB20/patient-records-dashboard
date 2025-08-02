DROP TABLE IF EXISTS HC_USER;
DROP TABLE IF EXISTS HC_ROLE;
DROP TABLE IF EXISTS USER_ROLE;

-- Users table for authentication
CREATE TABLE IF NOT EXISTS HC_USER (
    id serial PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    enabled BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
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

-- Insert default roles
INSERT INTO HC_ROLE (name, description) VALUES
    ('ROLE_ADMIN', 'Administrator with full access'),
    ('ROLE_DOCTOR', 'Medical doctor role'),
    ('ROLE_NURSE', 'Nursing staff role'),
    ('ROLE_STAFF', 'General staff role');

-- Insert default admin user (password: admin123)
INSERT INTO HC_USER (username, password, email, first_name, last_name, enabled, created_at, updated_at)
VALUES ('admin', '$2a$10$5qIV/EeK3Ssyxp7FYvG7fOMbsJWaF5p7hNREoax/uHQwARojiYG/G', 'admin@healthcare.com', 'System', 'Administrator', true, current_timestamp, current_timestamp);

-- Assign admin role to admin user
INSERT INTO USER_ROLE (user_id, role_id)
SELECT u.id, r.id 
FROM HC_USER u
CROSS JOIN HC_ROLE r
WHERE u.username = 'admin' AND r.name = 'ROLE_ADMIN';