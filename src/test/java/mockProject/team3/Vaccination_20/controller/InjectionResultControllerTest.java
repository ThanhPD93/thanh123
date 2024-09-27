package mockProject.team3.Vaccination_20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mockProject.team3.Vaccination_20.dto.injectionResultDto.InjectionResultRequestDto1;
import mockProject.team3.Vaccination_20.dto.injectionResultDto.InjectionResultResponseDto3;
import mockProject.team3.Vaccination_20.dto.injectionResultDto.UInjectionResultDTO;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import mockProject.team3.Vaccination_20.repository.InjectionResultRepository;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test") // Use test profile if you have specific test configuration
@AutoConfigureMockMvc
@WithMockUser
class InjectionResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    InjectionResultRepository injectionResultRepository;

    @Test
    @Sql( scripts ="/sql/injectionResultSql/jr-empty.sql")
    void testGetDocumentSuccess() throws Exception{
        mockMvc.perform(get("/api/injection-result/getAjax")
                        .param("filename","test.html"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql( scripts ="/sql/injectionResultSql/jr-empty.sql")
    void testGetDocumentFailed() throws Exception{
        mockMvc.perform(get("/api/injection-result/getAjax")
                        .param("filename",""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql( scripts ="/sql/injectionResultSql/jr-empty.sql")
    void testGetDocumentNotExist() throws Exception{
        mockMvc.perform(get("/api/injection-result/getAjax")
                        .param("filename","NotExist.html"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql( scripts ="/sql/injectionResultSql/jr-data.sql")
    void testFindAllWithOutSearchInput() throws Exception {
        mockMvc.perform(get("/api/injection-result/findBySearch")
                .param("searchInput","")
                .param("page","0")
                .param("size","10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

//    @Test
//    @Sql( scripts ="/sql/injectionResultSql/jr-data.sql")
//    void testFindAllWithSearchInput() throws Exception {
//        mockMvc.perform(get("/api/injection-result/findBySearch")
//                        .param("searchInput","John Doe")
//                        .param("page","0")
//                        .param("size","10")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content.length()").value(1));
//    }

    @Test
    @Sql( scripts ="/sql/injectionResultSql/jr-data.sql")
    void addInjectionResultSuccess() throws Exception {
        InjectionResultRequestDto1 request = new InjectionResultRequestDto1();
        request.setCustomerId("CUS01");
        request.setVaccineId("VAC001");
        request.setInjectionPlace("QuitePalace");
        request.setInjectionDate(LocalDate.of(2024,9,21));
        request.setNextInjectionDate(LocalDate.of(2024,10,1));
        request.setNumberOfInjection(1);
        request.setVaccineTypeId("VT001");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/injection-result/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql( scripts ="/sql/injectionResultSql/jr-data.sql")
    void addInjectionResultInvalid() throws Exception {
        InjectionResultRequestDto1 request = new InjectionResultRequestDto1();
        request.setCustomerId("CUS01");
        request.setVaccineId("VAC001");
        request.setInjectionPlace(null);
        request.setInjectionDate(LocalDate.of(2024,9,21));
        request.setNextInjectionDate(LocalDate.of(2024,10,1));
        request.setNumberOfInjection(1);
        request.setVaccineTypeId("VT001");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/injection-result/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql( scripts ="/sql/injectionResultSql/jr-data.sql")
    void testGetInjectionResultSuccess() throws Exception {
//        InjectionResultResponseDto3 response = new InjectionResultResponseDto3();
//        response.setInjectionResultId("IR003");
//        response.setInjectionDate(LocalDate.of(2023,1,1));
//        response.setVaccineFromInjectionResult("abc");
//        response.setInjectionPlace("A");
//        response.setCustomer("A");
//        response.setNumberOfInjection(1);
//
//
//        mockMvc.perform(get("/api/injection-result/detail/)"))
//                .andDo(print())
//                .andExpect(status().isOk());
    }


    @Test
    @Sql( scripts ="/sql/injectionResultSql/jr-empty.sql")
    void testDisplayDropdown() throws Exception {
        mockMvc.perform(get("/api/injection-result/displayDropdown"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql( scripts ="/sql/injectionResultSql/jr-empty.sql")
    void getAllInjectionPlaces() throws Exception {
        mockMvc.perform(get("/api/injection-result/places"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql( scripts ="/sql/injectionResultSql/jr-data.sql")
    void deleteInjectionResultsSuccess() throws Exception {
        List<String> ids = new ArrayList<>();
        ids.add("IR001");
        ids.add("IR002");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(ids);
        mockMvc.perform(delete("/api/injection-result/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk());
    }
}