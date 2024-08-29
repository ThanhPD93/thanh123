package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployee;
import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployeetest;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/employee/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }

    @GetMapping("/getEmployeesBySearch")
    public List<Employee> getEmployeesBySearch(@RequestParam String searchInput) {
        return employeeService.findBySearch(searchInput);
    }

    @GetMapping("/list")
    public ResponseEntity<List<LResponseEmployee>> listEmployees(Model model) {
    List<LResponseEmployee> employees = employeeService.getAll();
    return ResponseEntity.ok().body(employees);
    }
}
