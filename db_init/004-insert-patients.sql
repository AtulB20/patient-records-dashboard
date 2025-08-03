-- Insert sample patient data
INSERT INTO PATIENT (first_name, last_name, date_of_birth, gender, address, phone_number, email, blood_type, height_cm, weight_kg, emergency_contact_name, emergency_contact_phone, created_by, status) VALUES
('Rahul', 'Sharma', '1985-04-15', 'MALE', '123 Main Street, Mumbai, Maharashtra 400001', '+919876543210', 'rahul.sharma@example.com', 'O+', 175.5, 70.5, 'Priya Sharma', '+919876543211', 1, 'ACTIVE'),
('Priya', 'Patel', '1990-08-22', 'FEMALE', '23 Brigade Road, Bangalore, Karnataka 560001', '+917654321098', 'priyanka.patel@example.com', 'A-', 165.0, 58.2, 'Rahul Patel', '+917654321099', 1, 'ACTIVE'),
('Amit', 'Kumar', '1975-12-03', 'MALE', '78 Ring Road, Delhi 110001', '+919988776655', 'amit.kumar@example.com', 'B+', 180.0, 85.0, 'Neha Kumar', '+919988776656', 1, 'ACTIVE'),
('Neha', 'Gupta', '1982-11-08', 'FEMALE', '56 MG Road, Pune, Maharashtra 411001', '+918989898989', 'neha.gupta@example.com', 'A+', 170.0, 65.0, 'Rahul Gupta', '+918989898988', 1, 'ACTIVE'),
('Rajesh', 'Verma', '1970-09-14', 'MALE', '78 Mount Road, Chennai, Tamil Nadu 600002', '+917777888899', 'rajesh.verma@example.com', 'B-', 178.0, 82.0, 'Meera Verma', '+917777888900', 1, 'ACTIVE'),
('Pooja', 'Joshi', '1993-06-30', 'FEMALE', '90 MG Road, Ahmedabad, Gujarat 380001', '+918888999900', 'pooja.joshi@example.com', 'O+', 167.5, 60.0, 'Rahul Joshi', '+918888999901', 1, 'ACTIVE'),
('Ravi', 'Malhotra', '1980-02-17', 'MALE', '12 Connaught Place, New Delhi 110001', '+919999000011', 'ravi.malhotra@example.com', 'A-', 175.0, 75.0, 'Anjali Malhotra', '+919999000012', 1, 'INACTIVE'),
('Divya', 'Nair', '1987-10-05', 'FEMALE', '34 MG Road, Kochi, Kerala 682001', '+917777666655', 'divya.nair@example.com', 'AB+', 160.0, 55.0, 'Arjun Nair', '+917777666656', 1, 'ACTIVE'),
('Vikram', 'Reddy', '1978-05-21', 'MALE', '45 Banjara Hills, Hyderabad, Telangana 500034', '+919999111122', 'vikram.reddy@example.com', 'O-', 182.0, 78.5, 'Sneha Reddy', '+919999111123', 1, 'INACTIVE');
