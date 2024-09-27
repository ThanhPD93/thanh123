DELETE FROM injection_result;
DELETE FROM injection_schedule;
DELETE FROM vaccine;
DELETE FROM customer;
DELETE FROM vaccine_type;
DELETE FROM news;
DELETE FROM news_type;

INSERT INTO vaccine_type (vaccine_type_id, vaccine_type_description, vaccine_type_name, vaccine_type_status,vaccine_type_image)
VALUES
    ('UUID-001', 'This is a description for VaccineType 1', 'VaccineType 1', 1, '1'),
    ('UUID-002', 'This is a description for VaccineType 2', 'VaccineType 2', 1, '2'),
    ('UUID-003', 'This is a description for VaccineType 3', 'VaccineType 3', 1, '3'),
    ('UUID-004', 'This is a description for VaccineType 4', 'VaccineType 4', 1, '4'),
    ('UUID-005', 'This is a description for VaccineType 5', 'VaccineType 5', 1, '5');