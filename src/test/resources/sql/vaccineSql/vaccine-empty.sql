DELETE FROM employee;
DELETE FROM injection_result;
DELETE FROM injection_schedule;
DELETE FROM vaccine;
DELETE FROM customer;
DELETE FROM vaccine_type;
DELETE FROM news;
DELETE FROM news_type;

INSERT INTO vaccine_type (vaccine_type_id, vaccine_type_description, vaccine_type_name, vaccine_type_status,vaccine_type_image)
VALUES
    ('VT001', 'This is a description for VaccineType 1', 'VaccineType 1', 0, '1'),
    ('VT002', 'This is a description for VaccineType 2', 'VaccineType 2', 0, '2');