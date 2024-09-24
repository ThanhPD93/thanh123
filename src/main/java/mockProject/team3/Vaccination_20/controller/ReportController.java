package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.customerDto.CustomerListForReportDto;
import mockProject.team3.Vaccination_20.dto.injectionResultDto.InjectionResultResponseDto3;
import mockProject.team3.Vaccination_20.dto.report.ChartData;
import mockProject.team3.Vaccination_20.model.Customer;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto5;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.dto.report.ReportInjectionResultDto;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import mockProject.team3.Vaccination_20.service.CustomerService;
import mockProject.team3.Vaccination_20.service.InjectionResultService;
import mockProject.team3.Vaccination_20.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private InjectionResultService injectionResultService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private VaccineService vaccineService;
    //call
    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/report/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }

    @GetMapping("/customer/list")
    public ResponseEntity<Page<CustomerListForReportDto>> searchCustomers(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CustomerListForReportDto> result = customerService.searchCustomers(fullName, address, fromDate, toDate, page, size);
        return ResponseEntity.ok(result);
    }
	//

    @GetMapping("/customer/chart")
    public ResponseEntity<ChartData> getVaccinatedCustomerChartData(@RequestParam Integer year) {
        List<Object[]> resultList = injectionResultService.findCustomersVaccinatedByMonth(year);

        List<String> months = new ArrayList<>(Arrays.asList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        ));

        List<Integer> results = new ArrayList<>(Collections.nCopies(12, 0));

        for (Object[] result : resultList) {
            Integer monthIndex = (Integer) result[0];
            Integer total = ((Number) result[1]).intValue();

            // Gán giá trị cho tháng tương ứng
            if (monthIndex >= 1 && monthIndex <= 12) {
                results.set(monthIndex - 1, total);
            }
        }

        ChartData chartData = new ChartData();
        chartData.setMonths(months);
        chartData.setResults(results);

        return ResponseEntity.ok(chartData);
    }

	//report injection list
    @GetMapping("/injection/filter")
    public ResponseEntity<Page<ReportInjectionResultDto>> filterReportInjectionResults(@RequestParam(value = "startDate",required = false) LocalDate startDate,
                                                                                       @RequestParam (value = "endDate",required = false)LocalDate endDate,
                                                                                       @RequestParam(value = "vaccineTypeName",required = false) String vaccineTypeName,
                                                                                       @RequestParam (value = "vaccineName",required = false)String vaccineName,
                                                                                       @RequestParam (value = "page",required = false, defaultValue = "0")int page,
                                                                                       @RequestParam (value = "size",required = false, defaultValue = "10")int size){
		Page<ReportInjectionResultDto> reportInjectionResultDto = injectionResultService.filterReportInjection(startDate, endDate, vaccineTypeName, vaccineName, page, size);
        return ResponseEntity.ok(reportInjectionResultDto);
    }

    @GetMapping("/vaccine/chart")
    public ResponseEntity<ChartData> getVaccinatedChartData(@RequestParam Integer year) {
        List<Object[]> resultList = injectionResultService.findVaccineCountByMonth(year);

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

    @GetMapping("/injection/getYears")
    public ResponseEntity<List<Integer>> getYears() {
        List<Integer> years = injectionResultService.findDistinctYears();
        return ResponseEntity.ok(years);
    }
    @GetMapping("/vaccine/filter")
    public ResponseEntity<Page<VaccineResponseDto5>> getVaccineListForReport(@RequestParam(value = "beginDate", required = false,defaultValue = "") LocalDate beginDate,
                                                                             @RequestParam(value = "endDate", required = false,defaultValue = "") LocalDate endDate,
                                                                             @RequestParam(value = "vaccineTypeName", required = false,defaultValue = "") String vaccineTypeName,
                                                                             @RequestParam(value = "origin", required = false, defaultValue = "") String origin,
                                                                             @RequestParam(value = "page",required = false, defaultValue = "0") int page,
                                                                             @RequestParam(value = "size",required = false, defaultValue = "10") int size){
        Page<VaccineResponseDto5> vaccinePageForReport = vaccineService.vaccineListForReportByFilter(beginDate, endDate, vaccineTypeName, origin, page, size);
        return ResponseEntity.ok(vaccinePageForReport);
    }
}
