package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerService customerService;

    @Test
    @Sql(scripts = "/sql/")
    void getDocument() {
    }

    @Test
    @Sql(scripts = "/sql/")
    void findAll() {
    }

    @Test
    @Sql(scripts = "/sql/")
    void findAllWithPagination() {
    }

    @Test
    @Sql(scripts = "/sql/")
    void listEmployees() {
    }

    @Test
    @Sql(scripts = "/sql/")
    void addEmployee() {
    }

    @Test
    @Sql(scripts = "/sql/")
    void getEmployeeImage() {
    }

    @Test
    @Sql(scripts = "/sql/")
    void getEmployeeById() {
    }

    @Test
    @Sql(scripts = "/sql/")
    void deleteEmployees() {
    }
}