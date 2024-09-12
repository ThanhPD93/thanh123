package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.service.VaccineService;
import mockProject.team3.Vaccination_20.service.VaccineTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/vaccine-type")
public class VaccineTypeController {

    @Autowired
    private VaccineTypeService vaccineTypeService;

    @GetMapping
    List<VaccineType> getAllVaccineTypes() {
        return vaccineTypeService.getAllVaccineTypes();
    }

    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/vaccineType/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
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

}
