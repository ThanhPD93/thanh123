package mockProject.team3.Vaccination_20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mockProject.team3.Vaccination_20.dto.userDto.UserResponseDto1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.security.Principal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test") // Use test profile if you have specific test configuration
@AutoConfigureMockMvc
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(scripts = "/sql/employee-sql/test-data.sql")
    void testGetCurrentUsernameAndEmailSuccess() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/getCurrentUsernameAndEmail")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk());
////                .andExpect(jsonPath("$.username").value("Thanh"))
////                .andExpect(jsonPath("$.email").value("thanh@gmail.com"));
    }


    @Test
    void testGetCurrentUsernameFail() throws Exception {
        mockMvc.perform(get("/api/user/getCurrentUsernameAndEmail"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    void testGetRoleFail() throws Exception {
        mockMvc.perform(get("/api/user/getRole"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}