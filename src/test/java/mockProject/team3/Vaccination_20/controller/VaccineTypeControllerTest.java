package mockProject.team3.Vaccination_20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeRequestDto1;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeRequestDto2;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.utils.Status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser
class VaccineTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-data.sql")
    void findAllWithEmptySearchInput() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/findAll")
                        .param("searchInput", "")
                        .param("page", "0")
                        .param("size", "10")
                        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(10));
    }

    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-data.sql")
    void findAllWithSpecificSearchInput() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/findAll")
                        .param("searchInput", "VaccineType 5")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].vaccineTypeName").value("VaccineType 5"));
    }

    @Test
    @Sql("/sql/vaccineTypeSql/empty-table.sql")
    void testGetDocumentSuccess() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/getAjax")
                        .param("filename", "test.html"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql("/sql/vaccineTypeSql/empty-table.sql")
    void testGetDocumentFail() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/getAjax")
                        .param("filename", ""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/sql/vaccineTypeSql/empty-table.sql")
    void testAddVaccineTypeSuccess() throws Exception {
        VaccineTypeRequestDto1 request = new VaccineTypeRequestDto1();
        request.setVaccineTypeId("COVID-19");
        request.setVaccineTypeName("Pfizer");
        request.setVaccineTypeDescription("AAA");
        request.setVaccineTypeStatus(Status.ACTIVE);
        request.setVaccineTypeImage("null");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/vaccine-type/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Sql("/sql/vaccineTypeSql/vaccine-type-data.sql")
    void testAddVaccineTypeInvalidTest() throws Exception {
        VaccineTypeRequestDto1 request = new VaccineTypeRequestDto1();
        request.setVaccineTypeId("COVID-19");
        request.setVaccineTypeName("Pfizer");
        request.setVaccineTypeDescription("AAA");
        request.setVaccineTypeStatus(Status.ACTIVE);
        request.setVaccineTypeImage("A");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/vaccine-type/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
        VaccineType vaccineType = vaccineTypeRepository.findByVaccineTypeId("COVID-19");
        assertNull(vaccineType);
    }

    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-data.sql")
    void testGetVaccineTypeDetailSuccess() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/detail/UUID-001")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-data.sql")
    void testMakeInactive() throws Exception {
        List<String> vaccineTypeListIds = Arrays.asList("UUID-001", "UUID-002");
        VaccineTypeRequestDto2 lResponseVaccineType = new VaccineTypeRequestDto2();
        lResponseVaccineType.setVaccineTypeListIds(vaccineTypeListIds);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(lResponseVaccineType);

        mockMvc.perform(put("/api/vaccine-type/make-inactive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Made 2 vaccine types inactive successfully."));
        VaccineType vaccineType1 = vaccineTypeRepository.findByVaccineTypeId("UUID-001");
        VaccineType vaccineType2 = vaccineTypeRepository.findByVaccineTypeId("UUID-002");
        assertEquals(Status.INACTIVE, vaccineType1.getVaccineTypeStatus());
        assertEquals(Status.INACTIVE, vaccineType2.getVaccineTypeStatus());
    }

    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-data.sql")
    void testMakeInactiveFail() throws Exception {
        List<String> vaccineTypeListIds = Arrays.asList("UUID-001", "UUID-004");
        VaccineTypeRequestDto2 lResponseVaccineType = new VaccineTypeRequestDto2();
        lResponseVaccineType.setVaccineTypeListIds(vaccineTypeListIds);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(lResponseVaccineType);

        mockMvc.perform(put("/api/vaccine-type/make-inactive")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-data.sql")
    void testGetVaccineTypeImage() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/image/UUID-001"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-data.sql")
    void testGetVaccineTypeImageFailed() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/image/UUID-010"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-data.sql")
    void getAllVaccineTypesSuccess() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/vt-for-add-ir"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/inactive-data.sql")
    void getAllVaccineTypesFail() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/vt-for-add-ir"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}