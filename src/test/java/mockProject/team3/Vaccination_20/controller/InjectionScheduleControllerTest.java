package mockProject.team3.Vaccination_20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.InjectionScheduleRequestDto1;

import mockProject.team3.Vaccination_20.repository.InjectionScheduleRepository;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser
class InjectionScheduleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    InjectionScheduleRepository injectionScheduleRepository;

    @Test
    @Sql(scripts= "/sql/injectionSchedule/data.sql")
    void testFindAllSuccess() throws Exception {
        mockMvc.perform(get("/api/injection-schedule/findAll")
                        .param("searchInput","")
                        .param("page","0")
                        .param("size","10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    @Sql(scripts= "/sql/injectionSchedule/data.sql")
    void testFindBySearchVaccineName() throws Exception {
        mockMvc.perform(get("/api/injection-schedule/findAll")
                        .param("searchInput","COVID-19 Vaccine")
                        .param("page","0")
                        .param("size","10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    @Sql(scripts= "/sql/injectionSchedule/empty.sql")
    void TestGetDocumentSuccess() throws Exception {
        mockMvc.perform(get("/api/injection-schedule/getAjax")
                        .param("filename","test.html"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts= "/sql/injectionSchedule/empty.sql")
    void TestGetDocumentFail() throws Exception {
        mockMvc.perform(get("/api/injection-schedule/getAjax")
                        .param("filename",""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts= "/sql/injectionSchedule/empty.sql")
    void TestGetDocumentNotfound() throws Exception {
        mockMvc.perform(get("/api/injection-schedule/getAjax")
                        .param("filename","a.html"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    @Sql(scripts= "/sql/injectionSchedule/data.sql")
    void testAddSuccess() throws Exception {
        InjectionScheduleRequestDto1 request = new InjectionScheduleRequestDto1();
        request.setInjectionScheduleId("IS003");
        request.setVaccineId("VAC002");
        request.setInjectionScheduleDescription("Vaccination Schedule Description");
        request.setPlace("Place");
        request.setStartDate(LocalDate.of(2024, 10, 10));
        request.setEndDate(LocalDate.of(2024, 10, 11));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/injection-schedule/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts= "/sql/injectionSchedule/data.sql")
    void testAddFail() throws Exception {
        InjectionScheduleRequestDto1 request = new InjectionScheduleRequestDto1();
        request.setInjectionScheduleId("IS003");
        request.setVaccineId(null);
        request.setInjectionScheduleDescription("Vaccination Schedule Description");
        request.setPlace("Place");
        request.setStartDate(LocalDate.of(2024, 10, 10));
        request.setEndDate(LocalDate.of(2024, 10, 11));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/injection-schedule/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @Sql(scripts= "/sql/injectionSchedule/data.sql")
    void testFindByIdSuccess() throws Exception {
        mockMvc.perform(get("/api/injection-schedule/findById")
                .param("id","IS001"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts= "/sql/injectionSchedule/data.sql")
    void testFindByNotExistId() throws Exception {
        mockMvc.perform(get("/api/injection-schedule/findById")
                        .param("id","123"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}