package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineRequestDto1;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto3;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto4;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto5;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VaccineService {
    Page<VaccineResponseDto3> getVaccineListBySearchInput(String searchInput, int page, int size);
    VaccineResponseDto5 getVaccineById(String vaccineId);
    int createVaccine(VaccineRequestDto1 vaccineRequestDto1);
    int changeStatusVaccine(List<String> vaccineIds);
    List<String> importVaccineFromExcel(MultipartFile file) throws IOException;
	List<VaccineResponseDto4> findAllVaccineName();
}
