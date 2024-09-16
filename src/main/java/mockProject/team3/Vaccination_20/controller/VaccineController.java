package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.injectionresult.VaccineInfoDTO;
import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.repository.VaccineRepository;
import mockProject.team3.Vaccination_20.service.VaccineService;
import mockProject.team3.Vaccination_20.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "*")
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;
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

    @GetMapping("/detail/{vaccineInfoId}")
    public ResponseEntity<ApiResponse<VaccineInfoDTO>> getVaccineDetail(@PathVariable String vaccineInfoId) {
        Optional<Vaccine> vaccineOptional = vaccineRepository.findById(vaccineInfoId);

        if (vaccineOptional.isPresent()) {
            Vaccine vaccine = vaccineOptional.get();
            VaccineInfoDTO vaccineInfoDTO = new VaccineInfoDTO(vaccine.getVaccineId(), vaccine.getVaccineName());
            return ResponseEntity.ok(new ApiResponse<>(200, "Vaccine found", vaccineInfoDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "Vaccine not found", null));
        }
    }

}
