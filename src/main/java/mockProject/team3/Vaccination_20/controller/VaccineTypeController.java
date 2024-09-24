package mockProject.team3.Vaccination_20.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.*;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.service.VaccineTypeService;
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
import java.util.List;


@RestController
@RequestMapping("/api/vaccine-type")
public class VaccineTypeController {

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @Operation(summary = "Find all vaccine types with pagination")
    @GetMapping("/findAll")
    public ResponseEntity<Page<VaccineTypeResponseDto1>> findAllWithPagination(
            @RequestParam String searchInput,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<VaccineTypeResponseDto1> vaccineTypesPage = vaccineTypeService.findBySearchWithPagination(searchInput, page, size);
        return ResponseEntity.ok(vaccineTypesPage);
    }

    @Operation(summary = "Dynamically load HTML content using AJAX")
    @GetMapping("/getAjax")
    public ResponseEntity<String> getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/vaccine-type/" + filename);
        Path path = resource.getFile().toPath();

        if (!Files.exists(path)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found!");
        }

        return ResponseEntity.ok(Files.readString(path));
    }

    @Operation(summary = "Get all vaccine types for adding injection result")
    @GetMapping("/vt-for-add-ir")
    public ResponseEntity<List<VaccineTypeResponseDto5>> getAllVaccineTypes() {
        List<VaccineTypeResponseDto5> vaccineTypeResponseDto5s = vaccineTypeService.getAllVaccineTypes();
        return ResponseEntity.ok(vaccineTypeResponseDto5s);
    }

    @Operation(summary = "Add a new vaccine type")
    @PostMapping("/add")
    public ResponseEntity<String> addVaccineType(@Valid @RequestBody VaccineTypeRequestDto1 vaccineTypeRequestDto1) {
        int result = vaccineTypeService.addVaccineType(vaccineTypeRequestDto1);
        if (result == 0) {
            return ResponseEntity.badRequest().body("Cannot add new vaccine type due to image fault.");
        }
        return ResponseEntity.ok("Add new vaccine type success!");
    }

    @Operation(summary = "Get vaccine type details by ID")
    @GetMapping("/detail/{vaccineTypeId}")
    public ResponseEntity<VaccineTypeResponseDto2> getVaccineTypeDetail(@PathVariable String vaccineTypeId) {
        VaccineTypeResponseDto2 vaccineTypeResponseDto2 = vaccineTypeService.findById(vaccineTypeId);
        if (vaccineTypeResponseDto2 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vaccineTypeResponseDto2);
    }

    @Operation(summary = "Make vaccine types inactive")
    @PutMapping("/make-inactive")
    public ResponseEntity<String> makeInactive(@Valid @RequestBody VaccineTypeRequestDto2 vaccineTypeRequestDto2) {
        int count = vaccineTypeService.makeInactive(vaccineTypeRequestDto2.getVaccineTypeListIds());
        if (count > 0) {
            return ResponseEntity.ok("Made " + count + " vaccine types inactive successfully.");
        }
        return ResponseEntity.ok("No active vaccine types found.");
    }

    @Operation(summary = "Get vaccine type image by ID")
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getVaccineTypeImage(@PathVariable String id) {
        VaccineType vaccineType = vaccineTypeService.findVaccineTypeById(id);
        if (vaccineType == null || vaccineType.getVaccineTypeImage() == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] imageBytes = vaccineType.getVaccineTypeImage();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)  // Or IMAGE_PNG based on your image type
                .body(imageBytes);
    }
}
