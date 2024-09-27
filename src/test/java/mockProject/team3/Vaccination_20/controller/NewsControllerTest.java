package mockProject.team3.Vaccination_20.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mockProject.team3.Vaccination_20.dto.newsDto.NewsRequestDto1;
import mockProject.team3.Vaccination_20.model.News;
import mockProject.team3.Vaccination_20.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser
class NewsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NewsRepository newsRepository;

    @Test
    @Sql("/sql/news/data.sql")
    void testGetDocumentSuccess() throws Exception {
        mockMvc.perform(get("/api/news/getAjax")
                        .param("filename","test.html"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @Sql("/sql/news/data.sql")
    void testGetEmptyDocument() throws Exception {
        mockMvc.perform(get("/api/news/getAjax")
                        .param("filename",""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/sql/news/test-add.sql")
    void testAddNewsSuccess() throws Exception {
        NewsRequestDto1 newsRequestDto1 = new NewsRequestDto1();
        newsRequestDto1.setTitle("Test Title");
        newsRequestDto1.setContent("Test Content");
        newsRequestDto1.setPreview("Test Preview");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(newsRequestDto1);
        mockMvc.perform(post("/api/news/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql("/sql/news/test-add.sql")
    void testAddNewsFailed() throws Exception {
        NewsRequestDto1 newsRequestDto1 = new NewsRequestDto1();
        newsRequestDto1.setTitle("");
        newsRequestDto1.setContent("Test Content");
        newsRequestDto1.setPreview("Test Preview");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(newsRequestDto1);
        mockMvc.perform(post("/api/news/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/sql/news/data.sql")
    void testAddNewsWithNullValue() throws Exception {
        mockMvc.perform(post("/api/news/add"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/sql/news/data.sql")
    void testFindAllNewsSuccess() throws Exception {
        mockMvc.perform(get("/api/news/findAllNews")
                        .param("searchInput","")
                        .param("page","0")
                        .param("size","10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }

    @Test
    @Sql("/sql/news/data.sql")
    void testSearchByNewsId() throws Exception {
        mockMvc.perform(get("/api/news/findAllNews")
                        .param("searchInput","content 1")
                        .param("page","0")
                        .param("size","10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1));
    }

}