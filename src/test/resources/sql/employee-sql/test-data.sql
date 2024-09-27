DELETE FROM employee;
DELETE FROM injection_result;
DELETE FROM injection_schedule;
DELETE FROM vaccine;
DELETE FROM customer;
DELETE FROM vaccine_type;
DELETE FROM news;
DELETE FROM news_type;

INSERT INTO Employee (employee_Id, address, date_of_birth, email, employee_name, gender, image, password, phone, position, username, working_place) VALUES
('EM0001', '123 Main St', '1990-01-15', 'john.doe@example.com', 'John Doe', 0, 'image1.png', 'password1', '1234567890', 'Manager', 'jdoe', 'Office A'),
('EM0002', '456 Elm St', '1985-02-20', 'jane.smith@example.com', 'Jane Smith', 1, 'image2.png', 'password2', '1234567891', 'Developer', 'jsmith', 'Office B'),
('EM0003', '789 Oak St', '1992-03-25', 'alice.johnson@example.com', 'Alice Johnson', 1, 'image3.png', 'password3', '1234567892', 'Tester', 'ajohnson', 'Office C'),
('EM0004', '101 Pine St', '1991-04-10', 'bob.brown@example.com', 'Bob Brown', 0, 'image4.png', 'password4', '1234567893', 'Analyst', 'bbrown', 'Office D'),
('EM0005', '234 Cedar St', '1988-06-15', 'michael.clark@example.com', 'Michael Clark', 0, 'image5.png', 'password5', '1234567894', 'Designer', 'mclark', 'Office E'),
('EM0006', '567 Birch St', '1993-07-20', 'emily.wilson@example.com', 'Emily Wilson', 1, 'image6.png', 'password6', '1234567895', 'Consultant', 'ewilson', 'Office F'),
('EM0007', '890 Maple St', '1987-08-25', 'daniel.moore@example.com', 'Daniel Moore', 0, 'image7.png', 'password7', '1234567896', 'Support', 'dmoore', 'Office G'),
('EM0008', '111 Walnut St', '1994-09-30', 'sarah.hall@example.com', 'Sarah Hall', 1, 'image8.png', 'password8', '1234567897', 'HR', 'shall', 'Office H'),
('EM0009', '222 Cherry St', '1995-10-05', 'james.young@example.com', 'James Young', 0, 'image9.png', 'password9', '1234567898', 'Admin', 'jyoung', 'Office I'),
('EM0010', '333 Peach St', '1989-11-10', 'karen.king@example.com', 'Karen King', 1, 'image10.png', 'password10', '1234567899', 'Finance', 'kking', 'Office J'),
('EM0011', '444 Lime St', '1996-12-15', 'robert.wright@example.com', 'Robert Wright', 0, 'image11.png', 'password11', '1234567900', 'Legal', 'rwright', 'Office K'),
('EM0012', '555 Orange St', '1982-01-20', 'linda.scott@example.com', 'Linda Scott', 1, 'image12.png', 'password12', '1234567901', 'Marketing', 'lscott', 'Office L'),
('EM0013', '666 Lemon St', '1990-02-25', 'david.green@example.com', 'David Green', 0, 'image13.png', 'password13', '1234567902', 'Sales', 'dgreen', 'Office M'),
('EM0014', '777 Olive St', '1983-03-30', 'betty.adams@example.com', 'Betty Adams', 1, 'image14.png', 'password14', '1234567903', 'Operations', 'badams', 'Office N');