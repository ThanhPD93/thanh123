package mockProject.team3.Vaccination_20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerAddRequestDto;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test") // Use test profile if you have specific test configuration
public class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Test
    @Sql(scripts = "/setup-test-data.sql") // Run SQL script to set up database
    public void testFindAllCustomers() throws Exception {
        mockMvc.perform(get("/customer/findAllCustomers")
                        .param("searchInput", "")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].fullName").value("John Doe"))
                .andExpect(jsonPath("$.data.content[1].fullName").value("Jane Smith"));
    }

    @Test
    @Sql(scripts = "/sql/customer-empty.sql") // Run SQL script to set up database
    public void testAddCustomer() throws Exception {
        CustomerAddRequestDto requestDto = new CustomerAddRequestDto();
        requestDto.setCustomerId("3");
        requestDto.setFullName("Alice Johnson");
        requestDto.setAddress("789 Pine Rd");
        requestDto.setPassword("password123");

        mockMvc.perform(post("/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Add customer success!"));

        // Verify the customer is added
        assertNotNull(customerRepository.findById("3").orElse(null));
    }

    @Test
    @Sql(scripts = "/sql/setup-test-data.sql") // Run SQL script to set up database
    public void testFindById() throws Exception {
        mockMvc.perform(get("/customer/findById")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("John Doe"));
    }

    @Test
    @Sql(scripts = "/setup-test-data.sql") // Run SQL script to set up database
    @Sql(scripts = "/cleanup-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // Run SQL script to clean up database after test
    public void testDeleteCustomers() throws Exception {
        List<String> customerIds = List.of("1", "2");

        mockMvc.perform(delete("/customer/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerIds)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Customers deleted successfully"));

        // Verify customers are deleted
        assertEquals(0, customerRepository.count());
    }
}
