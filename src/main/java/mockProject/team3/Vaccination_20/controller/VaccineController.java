package mockProject.team3.Vaccination_20.controller;

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
//@CrossOrigin(origins = "*")
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;

    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/vaccine/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }

    @PostMapping("/add")
    public ResponseEntity<String> createVaccine(@RequestBody VaccineRequestDto1 vaccineRequestDto1) {
        int result = vaccineService.createVaccine(vaccineRequestDto1);
        return ResponseEntity.ok("add new vaccine success!");
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VaccineResponseDto3>> getVaccineListBySearchInput(
            @RequestParam("searchInput") String searchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<VaccineResponseDto3> vaccinePage = vaccineService.getVaccineListBySearchInput(searchInput, page, size);
        return ResponseEntity.ok(vaccinePage);
    }

    @GetMapping("/detail/{vaccineId}")
    public ResponseEntity<VaccineResponseDto5> getVaccineById(@PathVariable String vaccineId) {
        VaccineResponseDto5 vaccineResponseDto5 = vaccineService.getVaccineById(vaccineId);
        if(vaccineResponseDto5 == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(vaccineResponseDto5);
    }

    @PutMapping("/change-status")
    public ResponseEntity<String> updateVaccineStatus(@RequestBody List<String> vaccineIds){
        int result = vaccineService.changeStatusVaccine(vaccineIds);
        if (result == -2) return ResponseEntity.badRequest().body("could not find a vaccine with given vaccine IDs");
        if (result == -1) return ResponseEntity.badRequest().body("Please select only vaccines with In-active status");
        if (result == 0) return ResponseEntity.badRequest().body("no vaccine was selected");
        if (result == 1) return ResponseEntity.ok("Successfully changed 1 vaccine status to in-active");
        return ResponseEntity.ok("Successfully changed " + result + " vaccines' status to in-active");
    }

    @PostMapping("/import/excel")
    public ResponseEntity<String> importFromExcel(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload a valid Excel file.");
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

            return ResponseEntity.status(HttpStatus.OK).body(responseMessage.toString());

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload and import file.");
        }
    }

    @GetMapping("/findAllVaccineName")
    public ResponseEntity<List<VaccineResponseDto4>> findAllVaccineName() {
		return ResponseEntity.ok(vaccineService.findAllVaccineName());
    }
}
