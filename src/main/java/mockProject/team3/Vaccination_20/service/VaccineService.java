package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.model.Vaccine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

public interface VaccineService {

    //use for add-ir
    List<Vaccine> getAllVaccines();

    List<Vaccine> getVaccinesByType(String vaccineTypeId);

    Vaccine findByName(String vaccineName);
}
