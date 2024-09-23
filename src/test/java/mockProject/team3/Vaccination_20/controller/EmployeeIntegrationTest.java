package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser
class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Sql(scripts = "/sql/employee-sql/employee-empty.sql")
    public void testGetDocumentForEmployeeListResponse200() throws Exception{
        ResultActions result = mockMvc.perform(get("/api/employee/getAjax")
                        .param("filename", "AjaxTest.html"))
                .andExpect(status().isOk());
        result.andExpect(content()
                .string("This is ajax content for testing"));
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/employee-empty.sql")
    public void testGetDocumentForEmployeeListResponse400() throws Exception{
        mockMvc.perform(get("/api/employee/getAjax")
                        .param("filename", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ajax filename must not be empty!"));
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/employee-empty.sql")
    public void testGetDocumentForEmployeeListResponse404() throws Exception{
        mockMvc.perform(get("/api/employee/getAjax")
                        .param("filename", "non-existed.html"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("ajax file path not found!"));
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/test-data.sql")
    public void testFindByIdResponse200() throws Exception{
        mockMvc.perform(get("/api/employee/findById")
                        .param("employeeId", "EM0001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeName").value("John Doe"));
    }

    @Test
    @Sql(scripts = "/sql/employee-sql/test-data.sql")
    public void testFindByIdResponse404() throws Exception{
        mockMvc.perform(get("/api/employee/findById")
                        .param("employeeId", "EM0000"))
                .andExpect(status().isBadRequest());
    }
}