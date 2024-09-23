package mockProject.team3.Vaccination_20.controller;

import jakarta.validation.Valid;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.*;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.service.VaccineTypeService;
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
@RequestMapping("/api/vaccine-type")
public class VaccineTypeController {

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @GetMapping("/findAll")
    public Page<VaccineTypeResponseDto1> findAllWithPagination(@RequestParam String searchInput,
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
    public ResponseEntity<List<VaccineTypeResponseDto5>> getAllVaccineTypes() {
        List<VaccineTypeResponseDto5> vaccineTypeResponseDto5s = vaccineTypeService.getAllVaccineTypes();
		return ResponseEntity.ok(vaccineTypeResponseDto5s);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addVaccineType(@Valid @RequestBody VaccineTypeRequestDto1 vaccineTypeRequestDto1) {
        int result = vaccineTypeService.addVaccineType(vaccineTypeRequestDto1);
    	if (result == 0) {
            return ResponseEntity.badRequest().body("cannot add new vaccineType, due to image fault");
        }
        return ResponseEntity.ok("add new vaccine type success!");
    }

    @GetMapping("/detail/{vaccineTypeId}")
    public ResponseEntity<VaccineTypeResponseDto2> getVaccineTypeDetail(@PathVariable String vaccineTypeId) {
        return ResponseEntity.ok(vaccineTypeService.findById(vaccineTypeId));
    }

    @PutMapping("/make-inactive")
    public ResponseEntity<String> makeInactive(@RequestBody VaccineTypeRequestDto2 vaccineTypeRequestDto2) {
        int count = vaccineTypeService.makeInactive(vaccineTypeRequestDto2.getVaccineTypeListIds());
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
