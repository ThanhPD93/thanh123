package mockProject.team3.Vaccination_20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mockProject.team3.Vaccination_20.dto.employeeDto.EmployeeRequestDto1;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.repository.EmployeeRepository;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Sql(scripts = "/sql/employee-sql/employee-empty.sql")
    void testGetDocumentForEmployeeListResponse200() throws Exception{
        mockMvc.perform(get("/api/employee/getAjax")
                        .param("filename", "test.html"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/employee-empty.sql")
    void testGetDocumentForEmployeeListResponse400() throws Exception{
        mockMvc.perform(get("/api/employee/getAjax")
                        .param("filename", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ajax filename must not be empty!"));
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/employee-empty.sql")
    void testGetDocumentForEmployeeListResponse404() throws Exception{
        mockMvc.perform(get("/api/employee/getAjax")
                        .param("filename", "non-existed.html"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("ajax file path not found!"));
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/test-data.sql")
    void testFindByIdResponse200() throws Exception{
        mockMvc.perform(get("/api/employee/findById")
                        .param("employeeId", "EM0001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeName").value("John Doe"));
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/test-data.sql")
    void testFindByIdResponse404() throws Exception{
        mockMvc.perform(get("/api/employee/findById")
                        .param("employeeId", "EM0000"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/test-data.sql")
    void testFindAllWithOutSearchInput() throws Exception {
        mockMvc.perform(get("/api/employee/findAll")
                        .param("searchInput", "")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/test-data.sql")
    void testFindAllWithSearchInput() throws Exception {
        mockMvc.perform(get("/api/employee/findAll")
                        .param("searchInput", "EM0001")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].employeeName").value("John Doe"));
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/employee-empty.sql")
    void testAddEmployeeSuccess() throws Exception {
        EmployeeRequestDto1 request = new EmployeeRequestDto1();
        request.setEmployeeId("EM0001");
        request.setAddress("123 Main St");
        request.setDateOfBirth(LocalDate.of(1990, 1, 15));
        request.setEmail("john.doe@example.com");
        request.setEmployeeName("John Doe");
        request.setGender(Gender.MALE); // Assuming gender 0 is male; adjust accordingly
        // Example byte array representing an image (you should replace this with real image bytes)
        byte[] imageBytes = {113, 9}; // [113,09] as byte values
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        request.setImage(base64Image); // Setting image as Base64 string
        request.setPassword("password1");
        request.setPhone("1234567890");
        request.setPosition("Manager");
        request.setUsername("jdoe");
        request.setWorkingPlace("Office A");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk());

        Employee employee = employeeRepository.findByEmployeeId("EM0001");
        assertEquals("EM0001", employee.getEmployeeId());
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/employee-empty.sql")
    void testAddEmployeeInvalid() throws Exception {
        EmployeeRequestDto1 request = new EmployeeRequestDto1();
        request.setEmployeeId("EM0001");
        request.setAddress("123 Main St");
        request.setDateOfBirth(LocalDate.of(1990, 1, 15));
        request.setEmail("john.doe@example.com");
        request.setEmployeeName("John Doe");
        request.setGender(Gender.MALE); // Assuming gender 0 is male; adjust accordingly
        // Example byte array representing an image (you should replace this with real image bytes)
        byte[] imageBytes = {113, 9}; // [113,09] as byte values
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        request.setImage(base64Image); // Setting image as Base64 string
        request.setPassword("password1");
        request.setPhone("1234567890");
        request.setPosition("Manager");
        request.setUsername("");
        request.setWorkingPlace("Office A");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }



    @Test
    @Sql(scripts = "/sql/employee-sql/test-data.sql")
    void getEmployeeImageSuccess() throws Exception {
        mockMvc.perform(get("/api/employee/image/EM0001"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/test-data.sql")
    void testDeleteEmployeesSuccess() throws Exception{
        List<String> employeeIds = new ArrayList<>();
        employeeIds.add("EM0001");
        employeeIds.add("EM0002");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(employeeIds);
        mockMvc.perform(delete("/api/employee/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk());
        assertNull(employeeRepository.findByEmployeeId("EM0001"));
        assertNull(employeeRepository.findByEmployeeId("EM0002"));
    }


    @Test
    @Sql(scripts = "/sql/employee-sql/test-data.sql")
    void testDeleteEmployeesFailed() throws Exception{
        mockMvc.perform(delete("/api/employee/delete"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}