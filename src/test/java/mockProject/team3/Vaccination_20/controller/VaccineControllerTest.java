package mockProject.team3.Vaccination_20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineRequestDto1;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.utils.Status;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    void testGetDocumentSuccess() throws Exception {
        mockMvc.perform(get("/api/vaccine/getAjax")
                .param("filename", "test.html"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-empty.sql")
    void testGetDocumentFail() throws Exception {
        mockMvc.perform(get("/api/vaccine/getAjax")
                        .param("filename", ""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-empty.sql")
    void testCreateVaccineSuccess() throws Exception {
        VaccineRequestDto1 vaccineRequestDto1 = new VaccineRequestDto1();
        vaccineRequestDto1.setVaccineName("Vaccine Name 2");
        vaccineRequestDto1.setVaccineId("VAC001");
        vaccineRequestDto1.setVaccineTypeId("VT001");
        vaccineRequestDto1.setVaccineOrigin("Vaccine Origin 2");
        vaccineRequestDto1.setVaccineUsage("Vaccine Usage 2");
        vaccineRequestDto1.setVaccineStatus(Status.ACTIVE);
        vaccineRequestDto1.setIndication("Indication 2");
        vaccineRequestDto1.setContraindication("Contraindication 2");
        vaccineRequestDto1.setNumberOfInjection(2);
        vaccineRequestDto1.setTimeBeginNextInjection(LocalDate.of(2024, 2, 1));
        vaccineRequestDto1.setTimeEndNextInjection(LocalDate.of(2024, 3, 2));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequestBody = objectMapper.writeValueAsString(vaccineRequestDto1);
        mockMvc.perform(post("/api/vaccine/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-empty.sql")
    void testCreateVaccineNotFoundVaccineType() throws Exception {
        VaccineRequestDto1 vaccineRequestDto1 = new VaccineRequestDto1();
        vaccineRequestDto1.setVaccineName("Vaccine Name 2");
        vaccineRequestDto1.setVaccineId("VAC001");
        vaccineRequestDto1.setVaccineTypeId("VT003");
        vaccineRequestDto1.setVaccineOrigin("Vaccine Origin 2");
        vaccineRequestDto1.setVaccineUsage("Vaccine Usage 2");
        vaccineRequestDto1.setVaccineStatus(Status.ACTIVE);
        vaccineRequestDto1.setIndication("Indication 2");
        vaccineRequestDto1.setContraindication("Contraindication 2");
        vaccineRequestDto1.setNumberOfInjection(2);
        vaccineRequestDto1.setTimeBeginNextInjection(LocalDate.of(2024, 2, 1));
        vaccineRequestDto1.setTimeEndNextInjection(LocalDate.of(2024, 3, 2));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequestBody = objectMapper.writeValueAsString(vaccineRequestDto1);
        mockMvc.perform(post("/api/vaccine/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-data.sql")
    void testCreateVaccineNullVaccineId() throws Exception {
        VaccineRequestDto1 vaccineRequestDto1 = new VaccineRequestDto1();
        vaccineRequestDto1.setVaccineName("Vaccine Name");
        vaccineRequestDto1.setVaccineId(null);
        vaccineRequestDto1.setVaccineTypeId("VT001");
        vaccineRequestDto1.setVaccineOrigin("Vaccine Origin");
        vaccineRequestDto1.setVaccineUsage("Vaccine Usage");
        vaccineRequestDto1.setVaccineStatus(Status.ACTIVE);
        vaccineRequestDto1.setIndication("Indication");
        vaccineRequestDto1.setContraindication("Contraindication");
        vaccineRequestDto1.setNumberOfInjection(2);
        vaccineRequestDto1.setTimeBeginNextInjection(LocalDate.of(2024, 1, 1));
        vaccineRequestDto1.setTimeEndNextInjection(LocalDate.of(2024, 2, 2));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequestBody = objectMapper.writeValueAsString(vaccineRequestDto1);
        mockMvc.perform(post("/api/vaccine/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-data.sql")
    void testCreateVaccineNullVaccineType() throws Exception {
        VaccineRequestDto1 vaccineRequestDto1 = new VaccineRequestDto1();
        vaccineRequestDto1.setVaccineName("Vaccine Name");
        vaccineRequestDto1.setVaccineId("VAC001");
        vaccineRequestDto1.setVaccineTypeId(null);
        vaccineRequestDto1.setVaccineOrigin("Vaccine Origin");
        vaccineRequestDto1.setVaccineUsage("Vaccine Usage");
        vaccineRequestDto1.setVaccineStatus(Status.ACTIVE);
        vaccineRequestDto1.setIndication("Indication");
        vaccineRequestDto1.setContraindication("Contraindication");
        vaccineRequestDto1.setNumberOfInjection(2);
        vaccineRequestDto1.setTimeBeginNextInjection(LocalDate.of(2024, 1, 1));
        vaccineRequestDto1.setTimeEndNextInjection(LocalDate.of(2024, 2, 2));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonRequestBody = objectMapper.writeValueAsString(vaccineRequestDto1);
        mockMvc.perform(post("/api/vaccine/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-data.sql")
    void testGetVaccineList() throws Exception {
        mockMvc.perform(get("/api/vaccine/search")
                        .param("searchInput", "")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3));
    }

    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-data.sql")
    void testGetVaccineListBySearchInput() throws Exception {
        mockMvc.perform(get("/api/vaccine/search")
                        .param("searchInput", "VAC001")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].vaccineId").value("VAC001"));
    }

    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-data.sql")
    void testGetVaccineByIdSuccess() throws Exception {
        mockMvc.perform(get("/api/vaccine/detail/VAC001"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-data.sql")
    void testUpdateVaccineStatus() throws Exception {
        List<String> vaccineIds = Arrays.asList("VAC001", "VAC002");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(vaccineIds);
        mockMvc.perform(put("/api/vaccine/change-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-data.sql")
    void testUpdateVaccineStatusFail() throws Exception {
        List<String> vaccineIds = Arrays.asList("VAC001", "VAC003");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(vaccineIds);
        mockMvc.perform(put("/api/vaccine/change-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-empty.sql")
    void testExportTemplateSuccess() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/vaccine/export/excel"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .andExpect(header().string("Content-Disposition", "attachment; filename=import_template.xlsx"))
                .andReturn();
        byte[] responseBytes = result.getResponse().getContentAsByteArray();
        assertTrue(isValidExcelFile(responseBytes));
    }
    private boolean isValidExcelFile(byte[] content) {
        try {
            Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(content));
            Sheet sheet = workbook.getSheet("Import Template");
            return sheet != null && sheet.getRow(0) != null && "Vaccine ID".equals(sheet.getRow(0).getCell(0).getStringCellValue());
        } catch (IOException e) {
            return false;
        }
    }

    @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-empty.sql")  // Prepares the DB for the test
    void importFromExcel() throws Exception {
        mockMvc.perform(multipart("/api/vaccine/import"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

        @Test
    @Sql(scripts = "/sql/vaccineSql/vaccine-data.sql")
    void testFindAllVaccineNameSuccess() throws Exception {
        mockMvc.perform(get("/api/vaccine/findAllVaccineName"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}