package mockProject.team3.Vaccination_20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerRequestDto1;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerRequestDto2;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.utils.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test") // Use test profile if you have specific test configuration
@AutoConfigureMockMvc
@WithMockUser
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Sql(scripts = "/sql/customerSql/customer-data.sql") // Run SQL script to set up database
    void testFindAllCustomers() throws Exception {
        mockMvc.perform(get("/api/customer/findAllCustomers")
                        .param("searchInput", "")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(10));
    }

    @Test
    @Sql(scripts = "/sql/customerSql/customer-data.sql") // Run SQL script to set up database
    void testFindAllCustomersWithSearchInput() throws Exception {
        mockMvc.perform(get("/api/customer/findAllCustomers")
                        .param("searchInput", "John Doe")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].fullName").value("John Doe"));
    }

    @Test
    @Sql(scripts = "/sql/customerSql/customer-empty.sql") // Run SQL script to set up database
    void testAddCustomerSuccess() throws Exception {
        CustomerRequestDto1 requestDto = new CustomerRequestDto1();
        requestDto.setCustomerId("3");
        requestDto.setFullName("Alice Johnson");
        requestDto.setAddress("789 Pine Rd");
        requestDto.setPassword("password123");
        requestDto.setGender(Gender.MALE);
        requestDto.setEmail("alice@gmail.com");
        requestDto.setDateOfBirth(LocalDate.of(2000,1,1));
        requestDto.setPhone("0918730054");
        requestDto.setIdentityCard("123123");
        requestDto.setUsername("thanh");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequestBody = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/api/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/customerSql/customer-empty.sql") // Run SQL script to set up database
    void testAddCustomerInvalid() throws Exception {
        CustomerRequestDto1 requestDto = new CustomerRequestDto1();
        requestDto.setCustomerId("3");
        requestDto.setFullName(null);
        requestDto.setAddress("789 Pine Rd");
        requestDto.setPassword("password123");
        requestDto.setGender(Gender.MALE);
        requestDto.setEmail("alice@gmail.com");
        requestDto.setDateOfBirth(LocalDate.of(2000,1,1));
        requestDto.setPhone("0918730054");
        requestDto.setIdentityCard("123123");
        requestDto.setUsername("thanh");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequestBody = objectMapper.writeValueAsString(requestDto);
        mockMvc.perform(post("/api/customer/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    @Sql(scripts = "/sql/customerSql/customer-data.sql") // Run SQL script to set up database
    void testFindByIdSuccess() throws Exception {
        mockMvc.perform(get("/api/customer/findById")
                        .param("customerId", "CUS01"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/customerSql/customer-data.sql") // Run SQL script to set up database
    void testDeleteCustomersSuccess() throws Exception {
        CustomerRequestDto2 customerIds = new CustomerRequestDto2(List.of("CUS01","CUS02"));
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(customerIds);
        mockMvc.perform(delete("/api/customer/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andExpect(status().isOk());
        assertEquals(8, customerRepository.count());
    }

    @Test
    @Sql(scripts = "/sql/customerSql/customer-data.sql")
    void testGetDocument() throws Exception {
        mockMvc.perform(get("/api/customer/getAjax")
                        .param("filename","test.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("test success"));
    }

    @Test
    @Sql(scripts = "/sql/customerSql/customer-data.sql")
    void testGetDocumentNonExist() throws Exception {
        mockMvc.perform(get("/api/customer/getAjax")
                        .param("filename","non-exist.html"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/sql/customerSql/customer-data.sql")
    void testGetDocumentFailed() throws Exception {
        mockMvc.perform(get("/api/customer/getAjax")
                        .param("filename",""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

//    @Test
//    @Sql(scripts = "/sql/customerSql/customer-empty.sql")
//    void testGetCurrentUsername() throws Exception {
//        mockMvc.perform(get("/api/customer/getCurrentUsername"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }

    @Test
    @Sql(scripts = "/sql/customerSql/customer-data.sql")
    void testGetCustomerDetailSuccess() throws Exception {
        mockMvc.perform(get("/api/customer/detail/CUS01"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
