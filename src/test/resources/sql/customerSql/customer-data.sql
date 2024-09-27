DELETE FROM employee;
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
    ('CUS02', '456 Oak St', '1985-03-22', 'jane.smith@example.com', 'Jane Smith', 1, '234567890123', '$10$Zbmhx17XHQ475kBxCIDaB.rSnwjNAxCa7gRGzSxslvQKpDjPaS4/u', '0987654321', 'janesmith'),
    ('CUS03', '789 Pine St', '1992-11-03', 'michael.brown@example.com', 'Michael Brown', 0, '345678901234', '$10$Zbmhx17XHQ475kBxCIDaB.rSnwjNAxCa7gRGzSxslvQKpDjPaS4/u', '0123456781', 'michaelbrown'),
    ('CUS04', '321 Maple St', '1995-07-08', 'emily.white@example.com', 'Emily White', 1, '456789012345', '$10$Zbmhx17XHQ475kBxCIDaB.rSnwjNAxCa7gRGzSxslvQKpDjPaS4/u', '0987654322', 'emilywhite'),
    ('CUS05', '654 Elm St', '1988-04-12', 'daniel.jones@example.com', 'Daniel Jones', 0, '567890123456', '$10$Zbmhx17XHQ475kBxCIDaB.rSnwjNAxCa7gRGzSxslvQKpDjPaS4/u', '0123456783', 'danieljones'),
    ('CUS06', '987 Cedar St', '1991-10-18', 'laura.martin@example.com', 'Laura Martin', 1, '678901234567', '$10$Zbmhx17XHQ475kBxCIDaB.rSnwjNAxCa7gRGzSxslvQKpDjPaS4/u', '0987654323', 'lauramartin'),
    ('CUS07', '159 Spruce St', '1989-02-25', 'james.wilson@example.com', 'James Wilson', 0, '789012345678', '$10$Zbmhx17XHQ475kBxCIDaB.rSnwjNAxCa7gRGzSxslvQKpDjPaS4/u', '0123456784', 'jameswilson'),
    ('CUS08', '753 Birch St', '1993-06-30', 'sarah.lee@example.com', 'Sarah Lee', 1, '890123456789', '$10$Zbmhx17XHQ475kBxCIDaB.rSnwjNAxCa7gRGzSxslvQKpDjPaS4/u', '0987654324', 'sarahlee'),
    ('CUS09', '852 Walnut St', '1987-08-20', 'david.taylor@example.com', 'David Taylor', 0, '901234567890', '$10$Zbmhx17XHQ475kBxCIDaB.rSnwjNAxCa7gRGzSxslvQKpDjPaS4/u', '0123456785', 'davidtaylor'),
    ('CUS10', '951 Redwood St', '1994-12-09', 'olivia.moore@example.com', 'Olivia Moore', 1, '012345678901', '$10$Zbmhx17XHQ475kBxCIDaB.rSnwjNAxCa7gRGzSxslvQKpDjPaS4/u', '0987654325', 'oliviamoore');
