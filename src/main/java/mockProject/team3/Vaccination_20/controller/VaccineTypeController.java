package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.*;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.service.VaccineTypeService;
import mockProject.team3.Vaccination_20.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/vaccine-type")
public class VaccineTypeController {

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

    @GetMapping("/findAll")
    public Page<FindAllResponseVaccineType> findAllWithPagination(@RequestParam String searchInput,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "5") int size) {
        return vaccineTypeService.findBySearchWithPagination(searchInput, page, size);
    }

    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/vaccine-type/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }

    @GetMapping("/vt-for-add-ir")
    public ResponseEntity<List<Map<String, String>>> getAllVaccineTypes() {
        List<VaccineType> vaccineTypes = vaccineTypeService.getAllVaccineTypes();

        // Map only the needed fields
        List<Map<String, String>> vaccineTypeInfo = vaccineTypes.stream().map(vaccineType -> {
            Map<String, String> info = new HashMap<>();
            info.put("id", String.valueOf(vaccineType.getVaccineTypeId()));
            info.put("name", vaccineType.getVaccineTypeName());
            return info;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(vaccineTypeInfo);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addVaccineType(@RequestBody CRequestVaccineType cRequestVaccineType) {
        int result = vaccineTypeService.addVaccineType(cRequestVaccineType);
    	if (result == 0) {
            return ResponseEntity.badRequest().body("cannot add new vaccineType, due to image fault");
        }
        return ResponseEntity.ok("add new vaccine type success!");
    }

    @GetMapping("/vt-for-add-vaccine")
    public ResponseEntity<List<Map<String, String>>> getAllVaccineTypesForVaccineApi() {
        List<VaccineType> vaccineTypes = vaccineTypeService.getAllVaccineTypes();

        // Map only the needed fields
        List<Map<String, String>> vaccineTypeInfo = vaccineTypes.stream().map(vaccineType -> {
            Map<String, String> info = new HashMap<>();
            info.put("id", String.valueOf(vaccineType.getVaccineTypeId()));
            info.put("name", vaccineType.getVaccineTypeName());
            return info;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(vaccineTypeInfo);
    }

    @GetMapping("/detail/{vaccineTypeId}")
    public ResponseEntity<FindByIdResponseVaccineType> getVaccineTypeDetail(@PathVariable String vaccineTypeId) {
        return ResponseEntity.ok(vaccineTypeService.findById(vaccineTypeId));
    }

    @PutMapping("/make-inactive")
    public ResponseEntity<String> makeInactive(@RequestBody LResponseVaccineType lResponseVaccineType) {
        int count = vaccineTypeService.makeInactive(lResponseVaccineType.getVaccineTypeListIds());
        if (count != 0) {
            return ResponseEntity.ok("Made " + count + " vaccine types inactive successfully");
        }
        return ResponseEntity.ok("No active vaccine type found");
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getVaccineTypeImage(@PathVariable String id) {
        VaccineType vaccineType = vaccineTypeService.findVaccineTypeById(id);
        byte[] imageBytes = vaccineType.getVaccineTypeImage();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)  // Or IMAGE_PNG based on your image type
                .body(imageBytes);
    }
}
