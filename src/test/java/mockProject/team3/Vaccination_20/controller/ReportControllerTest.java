package mockProject.team3.Vaccination_20.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.repository.InjectionResultRepository;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WithMockUser
class ReportControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    VaccineRepository vaccineRepository;

    @Autowired
    InjectionResultRepository injectionResultRepository;

    @Test
    void testGetDocumentSuccess() throws Exception {
        mockMvc.perform(get("/api/report/getAjax")
                        .param("filename","test.html"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetDocumentFailed() throws Exception {
        mockMvc.perform(get("/api/report/getAjax")
                        .param("filename",""))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @Sql(scripts = "/sql/injectionResultSql/jr-data.sql")
    void testSearchCustomersReport() throws Exception {
        mockMvc.perform(get("/api/report/customer/list")
                        .param("address","")
                        .param("fullName", "John Doe")
                        .param("fromDate","")
                        .param("toDate","")
                        .param("page","0")
                        .param("size","10"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @Sql(scripts = "/sql/injectionResultSql/jr-data.sql")
    void testGetInjectionChartDataSuccess() throws Exception {
        mockMvc.perform(get("/api/report/injection/chart")
                        .param("year","2023"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/injectionResultSql/jr-data.sql")
    void testFilterReportInjectionResultsSuccess() throws Exception {
        mockMvc.perform(get("/api/report/injection/filter")
                        .param("startDate","")
                        .param("endDate", "")
                        .param("VaccineTypeName","VaccineType 1")
                        .param("VaccineName","")
                        .param("page","0")
                        .param("size","10"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/injectionResultSql/jr-data.sql")
    void testGetVaccinatedCustomerChartData() throws Exception {
        mockMvc.perform(get("/api/report/customer/chart")
                        .param("year","2023"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/injectionResultSql/jr-data.sql")
    void testGetVaccinatedChartData() throws Exception {
        mockMvc.perform(get("/api/report/vaccine/chart")
                        .param("year","2023"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetYears() throws Exception{
        mockMvc.perform(get("/api/report/injection/getYears"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "/sql/injectionResultSql/jr-data.sql")
    void getVaccineListForReport() throws Exception {
        mockMvc.perform(get("/api/report/vaccine/filter")
                        .param("beginDate","")
                        .param("endDate", "")
                        .param("VaccineTypeName","VaccineType 1")
                        .param("origin","")
                        .param("page","0")
                        .param("size","10"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}