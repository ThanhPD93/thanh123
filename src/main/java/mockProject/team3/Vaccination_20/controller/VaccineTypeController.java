package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.injectionresult.VaccineTypeInfoDTO;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.FindAllResponseVaccineType;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.CRequestVaccineType;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.DResponseVaccineType;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.LResponseVaccineType;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.repository.VaccineTypeRepository;
import mockProject.team3.Vaccination_20.service.VaccineService;
import mockProject.team3.Vaccination_20.service.VaccineTypeService;
import mockProject.team3.Vaccination_20.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/vaccine-type")
public class VaccineTypeController {

    @Autowired
    private VaccineTypeService vaccineTypeService;
    @Autowired
    private VaccineTypeRepository vaccineTypeRepository;

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
            info.put("id", vaccineType.getVaccineTypeId());
            info.put("name", vaccineType.getVaccineTypeName());
            return info;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(vaccineTypeInfo);
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

    @GetMapping("/detail/{vaccineTypeId}")
    public ResponseEntity<ApiResponse<VaccineTypeInfoDTO>> getVaccineTypeDetail(@PathVariable String vaccineTypeId) {
        Optional<VaccineType> vaccineTypeOptional = vaccineTypeRepository.findById(vaccineTypeId);

        if (vaccineTypeOptional.isPresent()) {
            VaccineType vaccineType = vaccineTypeOptional.get();
            VaccineTypeInfoDTO vaccineTypeDTO = new VaccineTypeInfoDTO(vaccineType.getVaccineTypeId(), vaccineType.getVaccineTypeName());
            return ResponseEntity.ok(new ApiResponse<>(200, "Vaccine type found", vaccineTypeDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Vaccine type not found", null));
        }
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

    @GetMapping("/findAllWithPagination")
    public Page<FindAllResponseVaccineType> findAllWithPagination(@RequestParam String searchInput,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int size) {
        return vaccineTypeService.findBySearchWithPagination(searchInput, page, size);
    }

}
