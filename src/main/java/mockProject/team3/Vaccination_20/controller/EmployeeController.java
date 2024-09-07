package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.request.forcreate.CRequestEmployee;
import mockProject.team3.Vaccination_20.dto.response.fordetail.DResponseEmployee;
import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployee;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.service.EmployeeService;
import mockProject.team3.Vaccination_20.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/employee/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }

    @GetMapping("/findAll")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }


//    @PutMapping("/update")
//    public ResponseEntity<DResponseEmployee> updateEmployee(@RequestBody URequestEmployee uRequestEmployee){
//        DResponseEmployee dResponseEmployee = employeeService.updateEmployee(uRequestEmployee);
//        return ResponseEntity.ok().body(dResponseEmployee);
//    }

    //show list employee
    @GetMapping("/findAllWithPagination")
    public Page<Employee> findAllWithPagination(@RequestParam String searchInput,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size) {
        return employeeService.findBySearchWithPagination(searchInput, page, size);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<LResponseEmployee>>> listEmployees(Model model) {
        List<LResponseEmployee> employees = employeeService.getAll();

        ApiResponse<List<LResponseEmployee>> response;
        if (employees.isEmpty()) {
            response = new ApiResponse<>(0, "Not Found!", new ArrayList<>());
        } else {
            response = new ApiResponse<>(1, "Success", employees);
        }

        return ResponseEntity.ok(response);
    }
    //add employee
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<DResponseEmployee>> addEmployee(@RequestBody CRequestEmployee cRequestEmployee) {
        DResponseEmployee dResponseEmployee = employeeService.addEmployee(cRequestEmployee);
        ApiResponse<DResponseEmployee> response;
        if (dResponseEmployee != null) {
            response = new ApiResponse<>(1, "Employee added successfully", dResponseEmployee);
        } else {
            response = new ApiResponse<>(0, "Failed to add employee", null);
        }

        return ResponseEntity.ok(response);
    }

    // update
//    @PutMapping("/update")
//    public ResponseEntity<ApiResponse<DResponseEmployee>> updateEmployee(@RequestBody URequestEmployee uRequestEmployee) {
//        DResponseEmployee dResponseEmployee = employeeService.updateEmployee(uRequestEmployee);
//
//        ApiResponse<DResponseEmployee> response;
//        if (dResponseEmployee != null) {
//            response = new ApiResponse<>(1, "Employee updated successfully", dResponseEmployee);
//        } else {
//            response = new ApiResponse<>(0, "Failed to update employee", null);
//        }
//
//        return ResponseEntity.ok(response);
//    }


    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getEmployeeImage(@PathVariable String id) {
        Employee employee = employeeService.findById(id);
        byte[] imageBytes = employee.getImage();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)  // Or IMAGE_PNG based on your image type
                .body(imageBytes);
    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        Employee employee = employeeService.findEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteEmployees(@RequestBody List<String> employeeIds) {
//        if (employeeIds == null || employeeIds.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No data deleted!");
//        }
//
//        try {
//            employeeService.deleteEmployees(employeeIds);
//            return ResponseEntity.ok("Employees deleted successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
//        }
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteEmployees(@RequestBody List<String> employeeIds) {
        if (employeeIds == null || employeeIds.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "No data deleted!", null));
        }

        try {
            employeeService.deleteEmployees(employeeIds);
            return ResponseEntity.ok(new ApiResponse<>(200, "Employees deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }


}