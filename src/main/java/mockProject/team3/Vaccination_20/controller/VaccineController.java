package mockProject.team3.Vaccination_20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineRequestDto1;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto3;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto4;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto5;
import mockProject.team3.Vaccination_20.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/vaccine")
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;

    @Operation(summary = "Dynamically load HTML content using AJAX")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AJAX HTML code loaded successfully!"),
            @ApiResponse(responseCode = "400", description = "Filename must not be empty!"),
            @ApiResponse(responseCode = "404", description = "File not found!")
    })
    @GetMapping("/getAjax")
    public ResponseEntity<String> getDocument(@RequestParam String filename) throws IOException {
        if (filename == null || filename.isEmpty()) {
            return ResponseEntity.badRequest().body("Filename must not be empty!");
        }

        ClassPathResource resource = new ClassPathResource("static/html/vaccine/" + filename);
        Path path = resource.getFile().toPath();

        if (!Files.exists(path)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found!");
        }

        return ResponseEntity.ok(Files.readString(path));
    }

    @Operation(summary = "Add a new vaccine")
    @PostMapping("/add")
    public ResponseEntity<String> createVaccine(@Valid @RequestBody VaccineRequestDto1 vaccineRequestDto1) {
        int result = vaccineService.createVaccine(vaccineRequestDto1);
        if (result > 0) {
            return ResponseEntity.ok("Add new vaccine success!");
        } else {
            return ResponseEntity.badRequest().body("Failed to add new vaccine.");
        }
    }

    @Operation(summary = "Get a list of vaccines by search input")
    @GetMapping("/search")
    public ResponseEntity<Page<VaccineResponseDto3>> getVaccineListBySearchInput(
            @RequestParam("searchInput") String searchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<VaccineResponseDto3> vaccinePage = vaccineService.getVaccineListBySearchInput(searchInput, page, size);
        return ResponseEntity.ok(vaccinePage);
    }

    @Operation(summary = "Get vaccine details by ID")
    @GetMapping("/detail/{vaccineId}")
    public ResponseEntity<VaccineResponseDto5> getVaccineById(@PathVariable String vaccineId) {
        VaccineResponseDto5 vaccineResponseDto5 = vaccineService.getVaccineById(vaccineId);
        if (vaccineResponseDto5 == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(vaccineResponseDto5);
    }

    @Operation(summary = "Update the status of vaccines")
    @PutMapping("/change-status")
    public ResponseEntity<String> updateVaccineStatus(@RequestBody List<String> vaccineIds) {
        int result = vaccineService.changeStatusVaccine(vaccineIds);
        if (result == -2) return ResponseEntity.badRequest().body("Could not find a vaccine with the given IDs.");
        if (result == -1) return ResponseEntity.badRequest().body("Please select only vaccines with inactive status.");
        if (result == 0) return ResponseEntity.badRequest().body("No vaccine was selected.");

        return ResponseEntity.ok("Successfully changed " + result + " vaccines' status to inactive.");
    }

    @Operation(summary = "Export vaccine template to Excel")
    @GetMapping("/export/excel")
    public void exportTemplate(HttpServletResponse response) throws IOException {
        vaccineService.exportTemplate(response);
    }

    @Operation(summary = "Import vaccines from Excel file")
    @PostMapping("/import/excel")
    public ResponseEntity<String> importFromExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a valid Excel file.");
        }
        try {
            List<String> notifications = vaccineService.importVaccineFromExcel(file); // Get notifications from service

            // Create the response message
            StringBuilder responseMessage = new StringBuilder("File uploaded and data imported successfully.");
            if (!notifications.isEmpty()) {
                responseMessage.append(" However, the following issues were found:\n");
                for (String notification : notifications) {
                    responseMessage.append(notification).append("\n");
                }
            }

            return ResponseEntity.ok(responseMessage.toString());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload and import file.");
        }
    }

    @Operation(summary = "Get all vaccine names")
    @GetMapping("/findAllVaccineName")
    public ResponseEntity<List<VaccineResponseDto4>> findAllVaccineName() {
        return ResponseEntity.ok(vaccineService.findAllVaccineName());
    }
}
