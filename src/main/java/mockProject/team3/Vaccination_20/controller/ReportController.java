package mockProject.team3.Vaccination_20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import mockProject.team3.Vaccination_20.utils.MSG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
//    static/html/report/
    @Operation(summary = "Using ajax to load content dynamically")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ajax html code loaded successfully!"),
            @ApiResponse(responseCode = "400", description = "ajax file name must not be empty!"),
            @ApiResponse(responseCode = "404", description = "ajax path could not find file!")
    })
    @GetMapping("/getAjax")
    public ResponseEntity<String> getDocument(@RequestParam String filename) throws IOException {
        // if user input filename that is empty or null -> return response 400 and appropriate message
        if(filename == null || filename.isEmpty()) {
            return ResponseEntity.badRequest().body(MSG.MSG31.getMessage());
        }
        ClassPathResource resource = new ClassPathResource("static/html/report/" + filename);
        Path path = resource.getFile().toPath();
        // if the path to the file cannot find the file -> return 404
        if (!Files.exists(path)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MSG.MSG32.getMessage());
        } else {
            // if file found, return response 200 and the file
            return ResponseEntity.ok(Files.readString(path));
        }
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

    @Operation(summary = "fetch chart for injection result")
    @ApiResponse(responseCode = "200", description = "Get chart success")
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

    @Operation(summary = "fetch chart for customer")
    @ApiResponse(responseCode = "200", description = "Get chart success")
    @GetMapping("/customer/chart")
    public ResponseEntity<ChartData> getVaccinatedCustomerChartData(@RequestParam Integer year) {
        List<Object[]> resultList = injectionResultService.findCustomersVaccinatedByMonth(year);

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

    @Operation(summary = "fetch chart for vaccine")
    @ApiResponse(responseCode = "200", description = "Get chart success")
    @GetMapping("/vaccine/chart")
    public ResponseEntity<ChartData> getVaccinatedChartData(@RequestParam Integer year) {
        List<Object[]> resultList = injectionResultService.findVaccineCountByMonth(year);

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

    @Operation(summary = "Get list of vaccine by filter with paginantion")
    @ApiResponse(responseCode = "200", description = "Get list success")
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
