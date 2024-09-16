package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.CRequestVaccineType;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.DResponseVaccineType;

import mockProject.team3.Vaccination_20.model.Vaccine;
import mockProject.team3.Vaccination_20.model.VaccineType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VaccineTypeService {
    List<VaccineType> getAllVaccineTypes();

    VaccineType addVaccineType(VaccineType vaccineType);

    VaccineType findVaccineTypeById(String id);

    DResponseVaccineType addVaccineType(CRequestVaccineType cRequestVaccineType);

    VaccineType findById(String id);

    int makeInactive(List<String> vaccineTypeIds);

    Page<VaccineType> findBySearchWithPagination(String searchInput, int page, int size);

}
