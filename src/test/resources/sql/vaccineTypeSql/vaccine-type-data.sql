DELETE FROM injection_result;
DELETE FROM injection_schedule;
DELETE FROM vaccine;
DELETE FROM customer;
DELETE FROM vaccine_type;
DELETE FROM news;
DELETE FROM news_type;

INSERT INTO vaccine_type (vaccine_type_id, vaccine_type_description, vaccine_type_name, vaccine_type_status,vaccine_type_image)
VALUES
    ('UUID-001', 'This is a description for VaccineType 1', 'VaccineType 1', 0, '1'),
    ('UUID-002', 'This is a description for VaccineType 2', 'VaccineType 2', 0, '2'),
    ('UUID-003', 'This is a description for VaccineType 3', 'VaccineType 3', 0, '3'),
    ('UUID-004', 'This is a description for VaccineType 4', 'VaccineType 4', 1, '4'),
    ('UUID-005', 'This is a description for VaccineType 5', 'VaccineType 5', 0, '5'),
    ('UUID-006', 'This is a description for VaccineType 6', 'VaccineType 6', 1, '6'),
    ('UUID-007', 'This is a description for VaccineType 7', 'VaccineType 7', 1, '7'),
    ('UUID-008', 'This is a description for VaccineType 8', 'VaccineType 8', 1, '8'),
    ('UUID-009', 'This is a description for VaccineType 9', 'VaccineType 9', 1, '9'),
    ('UUID-010', 'This is a description for VaccineType 10', 'VaccineType 10', 1, null);