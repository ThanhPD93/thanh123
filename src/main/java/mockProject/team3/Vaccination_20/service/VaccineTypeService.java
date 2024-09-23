package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeRequestDto1;

import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeResponseDto1;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeResponseDto2;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeResponseDto5;
import mockProject.team3.Vaccination_20.model.VaccineType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VaccineTypeService {
    List<VaccineTypeResponseDto5> getAllVaccineTypes();

    VaccineType addVaccineType(VaccineType vaccineType);

    VaccineType findVaccineTypeById(String id);

    int addVaccineType(VaccineTypeRequestDto1 vaccineTypeRequestDto1);

    VaccineTypeResponseDto2 findById(String id);

    int makeInactive(List<String> vaccineTypeIds);

    Page<VaccineTypeResponseDto1> findBySearchWithPagination(String searchInput, int page, int size);

}
