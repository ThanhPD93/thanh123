package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.forvaccine.VaccineDto;
import mockProject.team3.Vaccination_20.model.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VaccineService {
    Page<VaccineDto> getVaccineList(int page, int size);
    Page<VaccineDto> getVaccineListBySearchInput(String searchInput, int page, int size);
    VaccineDto getVaccineById(String vaccineId);
    VaccineDto createVaccine(VaccineDto vaccineDto);
    VaccineDto updateVaccine(String vaccineId, VaccineDto vaccineDto);
    void changeStatusVaccine(List<String> vaccineIds);
    void importVaccineFromExcel(MultipartFile file) throws IOException;
}
