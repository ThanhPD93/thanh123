package mockProject.team3.Vaccination_20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeRequestDto1;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeRequestDto2;
import mockProject.team3.Vaccination_20.model.VaccineType;
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

import java.nio.file.Files;
import java.nio.file.Paths;
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
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-10-data.sql")
    void findAllWithEmptySearchInput() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/findAll")
                        .param("searchInput", "")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(10))
                .andExpect(jsonPath("$.content[0].vaccineTypeName").value("VaccineType 1"))
                .andExpect(jsonPath("$.content[1].vaccineTypeName").value("VaccineType 2"))
                .andExpect(jsonPath("$.content[2].vaccineTypeName").value("VaccineType 3"))
                .andExpect(jsonPath("$.content[3].vaccineTypeName").value("VaccineType 4"))
                .andExpect(jsonPath("$.content[4].vaccineTypeName").value("VaccineType 5"))
                .andExpect(jsonPath("$.content[5].vaccineTypeName").value("VaccineType 6"))
                .andExpect(jsonPath("$.content[6].vaccineTypeName").value("VaccineType 7"))
                .andExpect(jsonPath("$.content[7].vaccineTypeName").value("VaccineType 8"))
                .andExpect(jsonPath("$.content[8].vaccineTypeName").value("VaccineType 9"))
                .andExpect(jsonPath("$.content[9].vaccineTypeName").value("VaccineType 10"));
    }

    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-10-data.sql")
    void findAllWithSpecificSearchInput() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/findAll")
                        .param("searchInput", "VaccineType 5")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].vaccineTypeName").value("VaccineType 5"));
    }

    @Test
    @Sql("/sql/vaccineTypeSql/empty-table.sql")
    void getDocument() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/getAjax")
                        .param("filename", "test.html"))
                .andDo(print())
                .andExpect(status().isOk());
                   // Assuming $.content should contain "Test success"
    }

    @Test
    @Sql("/sql/vaccineTypeSql/empty-table.sql")
    void addVaccineTypeTest() throws Exception {
        // Create an instance of the request object
        VaccineTypeRequestDto1 request = new VaccineTypeRequestDto1();
        request.setVaccineTypeId("COVID-19");
        request.setVaccineTypeName("Pfizer");
        request.setVaccineTypeDescription("AAA");
        request.setVaccineTypeStatus(Status.INACTIVE);
        request.setVaccineTypeImage("null");
        // Use ObjectMapper to convert the object to a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(request);
        mockMvc.perform(post("/api/vaccine-type/add")  // POST request
                        .contentType(MediaType.APPLICATION_JSON)  // Request body is JSON
                        .content(jsonRequestBody))  // Sending the JSON body
                .andDo(print())  // Print the result (for debugging)
                .andExpect(status().isOk());// Expect HTTP 200 OK
        VaccineType vaccineType = vaccineTypeRepository.findByVaccineTypeId("COVID-19");
        assertEquals("COVID-19", vaccineType.getVaccineTypeId());
    }

    @Test
    @Sql("/sql/vaccineTypeSql/empty-table.sql")
    void addVaccineTypeNullValueTest() throws Exception {
        VaccineTypeRequestDto1 request = new VaccineTypeRequestDto1();
        request.setVaccineTypeId("COVID-19");
        request.setVaccineTypeName("Pfizer");
        request.setVaccineTypeDescription("AAA");
        request.setVaccineTypeStatus(Status.INACTIVE);
        request.setVaccineTypeImage(null);  // Image can be null, but ensure validation is correct

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/vaccine-type/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
        // Expecting 400 error now
    }

    @Test
    @Sql("/sql/vaccineTypeSql/vaccine-type-10-data.sql")
    void addVaccineTypeInvalidTest() throws Exception {
        VaccineTypeRequestDto1 request = new VaccineTypeRequestDto1();
        request.setVaccineTypeId("UUID-001"); // Vaccine Type Id is unique
        request.setVaccineTypeName("Pfizer");
        request.setVaccineTypeDescription("AAA");
        request.setVaccineTypeStatus(Status.INACTIVE);
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
        // Expecting 400 error now
    }

    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-10-data.sql")
    void getVaccineTypeDetailSuccess() throws Exception {
        mockMvc.perform(get("/api/vaccine-type/detail/UUID-001")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/vaccine-type-10-data.sql")
    void makeInactive() throws Exception {
        List<String> vaccineTypeListIds = Arrays.asList("UUID-001", "UUID-002");
        VaccineTypeRequestDto2 lResponseVaccineType = new VaccineTypeRequestDto2();
        lResponseVaccineType.setVaccineTypeListIds(vaccineTypeListIds);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = objectMapper.writeValueAsString(lResponseVaccineType);

        mockMvc.perform(put("/api/vaccine-type/make-inactive?=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Made 2 vaccine types inactive successfully"));
        VaccineType vaccineType1 = vaccineTypeRepository.findByVaccineTypeId("UUID-001");
        VaccineType vaccineType2 = vaccineTypeRepository.findByVaccineTypeId("UUID-002");
        assertEquals(Status.INACTIVE, vaccineType1.getVaccineTypeStatus());
        assertEquals(Status.INACTIVE, vaccineType2.getVaccineTypeStatus());
    }

    @Test
    @Sql(scripts = "/sql/vaccineTypeSql/image-test.sql")  // Preload SQL data
    void testGetVaccineTypeImage() throws Exception {
        // Step 1: Load the expected image from resources
        byte[] expectedImageBytes = Files.readAllBytes(Paths.get("src/test/resources/images/test2.png"));
        // Step 2: Perform a GET request to retrieve the image by its ID
        mockMvc.perform(get("/api/vaccine-type/image/UUID-001"))  // The ID should match the one in the SQL script
                .andExpect(status().isOk())  // Expect 200 OK
                .andExpect(content().contentType(MediaType.IMAGE_JPEG))  // Adjust content type if needed (JPEG or PNG)
                .andExpect(content().bytes(expectedImageBytes));  // Compare the byte arrays of the image
    }

}