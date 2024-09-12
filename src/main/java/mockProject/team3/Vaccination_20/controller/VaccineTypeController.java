package mockProject.team3.Vaccination_20.controller;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.CRequestVaccineType;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.DResponseVaccineType;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.LResponseVaccineType;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.service.VaccineTypeService;
import mockProject.team3.Vaccination_20.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/vaccine-type")
public class VaccineTypeController {

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @GetMapping("/findAllWithPagination")
    public Page<VaccineType> findAllWithPagination(@RequestParam String searchInput,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size) {
        return vaccineTypeService.findBySearchWithPagination(searchInput, page, size);
    }

//    @GetMapping
//    List<VaccineType> getAllVaccineTypes() {
//        return vaccineTypeService.getAllVaccineTypes();
//    }

    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/vaccine-type/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<DResponseVaccineType>> addVaccineType(@RequestBody CRequestVaccineType cRequestVaccineType) {
        DResponseVaccineType dResponseVaccineType = vaccineTypeService.addVaccineType(cRequestVaccineType);
        ApiResponse<DResponseVaccineType> response;
        if (dResponseVaccineType != null) {
            response = new ApiResponse<>(1, "Vaccine Type added successfully", dResponseVaccineType);
        } else {
            response = new ApiResponse<>(0, "Failed to add vaccine type", null);
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/make-inactive")
    public ResponseEntity<String> makeInactive(@RequestBody LResponseVaccineType lResponseVaccineType) {
        System.out.println("Inside CONTROLLER: " + lResponseVaccineType.getVaccineTypeListIds());
        int count = vaccineTypeService.makeInactive(lResponseVaccineType.getVaccineTypeListIds());
        if(count != 0){
            return ResponseEntity.ok("Make inactive " + count + " vaccine types successfully");
        }
        return ResponseEntity.ok("No vaccine type that is active");
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<VaccineType> getEmployeeById(@PathVariable String id) {
        VaccineType vaccineType = vaccineTypeService.findVaccineTypeById(id);
        if (vaccineType != null) {
            return ResponseEntity.ok(vaccineType);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getVaccineTypeImage(@PathVariable String id) {
        VaccineType vaccineType = vaccineTypeService.findById(id);
        byte[] imageBytes = vaccineType.getVaccineTypeImage();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)  // Or IMAGE_PNG based on your image type
                .body(imageBytes);
    }
}

