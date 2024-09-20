package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.report.ChartData;
import mockProject.team3.Vaccination_20.repository.InjectionResultRepository;
import mockProject.team3.Vaccination_20.service.CustomerService;
import mockProject.team3.Vaccination_20.service.InjectionResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private InjectionResultService injectionResultService;
    @Autowired
    private CustomerService customerService;
    //call
    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/report/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }


    @GetMapping("/injection/getYears")
    public ResponseEntity<List<Integer>> getYears() {
        List<Integer> years = injectionResultService.findDistinctYears();
        return ResponseEntity.ok(years);
    }


    @GetMapping("/injection/chart")
    public ResponseEntity<ChartData> getChartData(@RequestParam Integer year) {
        List<Object[]> resultList = injectionResultService.findInjectionResultsByYear(year);

        List<String> months = new ArrayList<>(Arrays.asList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        ));

        List<Integer> results = new ArrayList<>(Collections.nCopies(12, 0));

        for (Object[] result : resultList) {
            Integer monthIndex = (Integer) result[0];
            Integer total = ((Number) result[1]).intValue();

            if (monthIndex >= 1 && monthIndex <= 12) {
                results.set(monthIndex - 1, total);
            }
        }

        ChartData chartData = new ChartData();
        chartData.setMonths(months);
        chartData.setResults(results);

        return ResponseEntity.ok(chartData);
    }

    @GetMapping("/customer/chart")
    public ResponseEntity<ChartData> getVaccinatedCustomerChartData(@RequestParam Integer year) {
        List<Object[]> resultList = customerService.findCustomersVaccinatedByMonth(year);

        List<String> months = new ArrayList<>(Arrays.asList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        ));

        List<Integer> results = new ArrayList<>(Collections.nCopies(12, 0));

        for (Object[] result : resultList) {
            Integer monthIndex = (Integer) result[0]; // Lấy chỉ số tháng (1-12)
            Integer total = ((Number) result[1]).intValue();

            // Gán giá trị cho tháng tương ứng
            if (monthIndex >= 1 && monthIndex <= 12) {
                results.set(monthIndex - 1, total); // Cập nhật giá trị
            }
        }

        ChartData chartData = new ChartData();
        chartData.setMonths(months);
        chartData.setResults(results);

        return ResponseEntity.ok(chartData);
    }


}
