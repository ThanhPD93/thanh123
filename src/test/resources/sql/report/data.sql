DELETE FROM injection_result;
DELETE FROM injection_schedule;
DELETE FROM vaccine;
DELETE FROM customer;
DELETE FROM vaccine_type;
DELETE FROM news;
DELETE FROM news_type;

INSERT INTO customer (customer_id, address, date_of_birth, email, full_name, gender, identity_card, password, phone, username)
VALUES
    ('CUS01', '123 Main St', '1990-01-15', 'john.doe@example.com', 'John Doe', 0, '123456789012', '$10$tf2GfQB.yvJ/C5oOQ8FQAuoc/NjkHTpw8a94/VhB5eJjkF40NANLK', '0123456789', 'johndoe'),
    ('CUS02', '456 Oak St', '1985-03-22', 'jane.smith@example.com', 'Jane Smith', 1, '234567890123', '$10$Zbmhx17XHQ475kBxCIDaB.rSnwjNAxCa7gRGzSxslvQKpDjPaS4/u', '0987654321', 'janesmith');

INSERT INTO injection_result (injection_result_id, injection_date, injection_place, next_injection_date, number_of_injection, prevention, customer_customer_id, vaccine_from_injection_result_vaccine_id)
VALUES
    ('IR001', '2023-01-12', 'A1', '2023-07-12', 1, 'Wear a mask', 'CUS01', 'VAC001'),
    ('IR002', '2023-02-10', 'B2', '2023-08-10', 1, 'Stay hydrated', 'CUS02', 'VAC002');