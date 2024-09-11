package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vaccine")
@CrossOrigin(origins = "*")
public class VaccineController {
    @Autowired
    private VaccineService vaccineService;


//    use for add injection-rs
//    @GetMapping("/v-for-add-ir")
//    public ResponseEntity<List<Map<String, String>>> getAllVaccines() {
//        List<Vaccine> vaccines = vaccineService.getAllVaccines();
//
//        // Map only the needed fields
//        List<Map<String, String>> vaccineInfo = vaccines.stream().map(vaccine -> {
//            Map<String, String> info = new HashMap<>();
//            info.put("id", vaccine.getVaccineId());
//            info.put("name", vaccine.getVaccineName());
//            return info;
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.ok(vaccineInfo);
//    }

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



}
