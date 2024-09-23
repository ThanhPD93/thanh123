package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser
class VaccineControllerTest {
	@Autowired
    private MockMvc mockMvc;
    @Autowired
    private VaccineRepository vaccineRepository;

    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-empty.sql")
    void getDocument() throws Exception {
        mockMvc.perform(get("/api/vaccine/getAjax")
                .param("filename", "test.html"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("test success"));
    }

//    @Test
//    @Sql(scripts = "/sql/vaccineSql/vaccine-empty.sql")
//    void createVaccine() {
//    }
//
//    @Test
//    @Sql(scripts = "/sql/vaccineSql/vaccine-empty.sql")
//    void getVaccineList() {
//    }
//
//    @Test
//    void getVaccineListBySearchInput() {
//    }
//
//    @Test
//    void getVaccineById() {
//    }
//
//    @Test
//    void updateVaccine() {
//    }
//
//    @Test
//    void updateVaccineStatus() {
//    }
//
//    @Test
//    void importFromExcel() {
//    }
//
//    @Test
//    void getVaccinesByType() {
//    }
}