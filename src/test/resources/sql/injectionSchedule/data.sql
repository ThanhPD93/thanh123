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

INSERT INTO vaccine (vaccine_id, contraindication, indication, number_of_injection,time_begin_next_injection, time_end_next_injection, vaccine_name,vaccine_origin, vaccine_status, vaccine_usage, vaccine_type_vaccine_type_id)
VALUES
    ('VAC001', 'None', 'For individuals above 18', 2, '2023-01-01', '2023-07-01', 'COVID-19 Vaccine', 'USA', 0, 'Prevents COVID-19', 'VT001'),
    ('VAC002', 'Pregnancy', 'For children aged 5-12', 1, '2023-03-01', '2023-09-01', 'Flu Vaccine', 'UK', 0, 'Prevents Influenza', 'VT002');

INSERT INTO injection_schedule (injection_schedule_id,injection_schedule_description,
                                end_date,place,start_date,status,vaccine_from_injection_schedule_vaccine_id
)
VALUES
    ('IS001',  'First round of vaccinations for Group A.',
     '2024-12-31',  'City Hospital',  '2024-11-01', 0,  'VAC001' ),
    ('IS002','Second round of vaccinations for Group B.',
     '2024-12-15','Community Health Center','2024-10-01',  1,'VAC002' );