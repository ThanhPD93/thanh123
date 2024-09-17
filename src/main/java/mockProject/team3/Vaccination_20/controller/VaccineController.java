package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.forvaccine.VaccineDto;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.dto.injectionresult.VaccineInfoDTO;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.service.VaccineService;
import mockProject.team3.Vaccination_20.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vaccine")
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
    public ResponseEntity<VaccineDto> createVaccine(@RequestBody VaccineDto vaccineDto) {
        System.out.println("Received VaccineDto: " + vaccineDto);
        VaccineDto createdVaccine = vaccineService.createVaccine(vaccineDto);
        return new ResponseEntity<>(createdVaccine, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<VaccineDto>> getVaccineList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<VaccineDto> vaccinePage = vaccineService.getVaccineList(page, size);
        return ResponseEntity.ok(vaccinePage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VaccineDto>> getVaccineListBySearchInput(
            @RequestParam("searchInput") String searchInput,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<VaccineDto> vaccinePage = vaccineService.getVaccineListBySearchInput(searchInput, page, size);
        return ResponseEntity.ok(vaccinePage);
    }

    @GetMapping("/detail/{vaccineId}")
    public ResponseEntity<VaccineDto> getVaccineById(@PathVariable String vaccineId) {
        VaccineDto vaccineDto = vaccineService.getVaccineById(vaccineId);
        return ResponseEntity.ok(vaccineDto);
    }

    @PutMapping("/update/{vaccineId}")
    public ResponseEntity<VaccineDto> updateVaccine(
            @PathVariable String vaccineId,
            @RequestBody VaccineDto vaccineDto) {
        VaccineDto updatedVaccine = vaccineService.updateVaccine(vaccineId, vaccineDto);
        return ResponseEntity.ok(updatedVaccine);
    }

    @PutMapping("/change-status")
    public ResponseEntity<ApiResponse<String>> updateVaccineStatus(@RequestBody List<String> vaccineIds){
        if (vaccineIds == null || vaccineIds.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "No data deleted!", null));
        }
        try {
            vaccineService.changeStatusVaccine(vaccineIds);
            return ResponseEntity.ok(new ApiResponse<>(200, "Vaccines update successfully", null));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred: " + e.getMessage(), null));
        }
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
    @Autowired
    private VaccineRepository vaccineRepository;

//    use for add injection-rs

    @GetMapping("/v-for-add-ir")
    public ResponseEntity<List<Map<String, String>>> getVaccinesByType(@RequestParam(required = false) String vaccineTypeId) {
        List<Vaccine> vaccines = vaccineService.getVaccinesByType(vaccineTypeId);

        // Map only the needed fields
        List<Map<String, String>> vaccineInfo = vaccines.stream().map(vaccine -> {
            Map<String, String> info = new HashMap<>();
            info.put("id", vaccine.getVaccineId());
            info.put("name", vaccine.getVaccineName());
            return info;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(vaccineInfo);
    }

//    @GetMapping("/detail/{vaccineInfoId}")
//    public ResponseEntity<ApiResponse<VaccineInfoDTO>> getVaccineDetail(@PathVariable String vaccineInfoId) {
//        Optional<Vaccine> vaccineOptional = vaccineRepository.findById(vaccineInfoId);
//
//        if (vaccineOptional.isPresent()) {
//            Vaccine vaccine = vaccineOptional.get();
//            VaccineInfoDTO vaccineInfoDTO = new VaccineInfoDTO(vaccine.getVaccineId(), vaccine.getVaccineName());
//            return ResponseEntity.ok(new ApiResponse<>(200, "Vaccine found", vaccineInfoDTO));
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new ApiResponse<>(404, "Vaccine not found", null));
//        }
//    }

}
