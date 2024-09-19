package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.report.InjectionResultStats;
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
import java.util.List;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private InjectionResultService injectionResultService;
    //call
    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/report/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }

    @GetMapping("/api/injection-results/stats")
    public ResponseEntity<List<InjectionResultStats>> getInjectionResultsByMonth(@RequestParam int year) {
        List<InjectionResultStats> stats = injectionResultService.getInjectionResultsByYear(year);
        return ResponseEntity.ok(stats);
    }

}
