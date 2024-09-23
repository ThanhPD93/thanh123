package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.injectionResultDto.*;
import org.springframework.data.domain.Page;

import java.util.List;


public interface InjectionResultService {

    Page<InjectionResultResponseDto1> findBySearchWithPagination(String searchInput, int page, int size);
    List<InjectionResultResponseDto1> getAllInjectionResults();
    List<String> getAllInjectionPlaces();
    int addInjectionResult(InjectionResultRequestDto1 dto);
    UInjectionResultDTO getInjectionResultById(String id);
    UInjectionResultDTO updateInjectionResult(String injectionResultId, UInjectionResultDTO uInjectionResultDTO);
    void deleteInjectionResults(List<String> ids);
    Page<InjectionResultResponseDto3> findBySearch(String searchInput, int page, int size);
	InjectionResultResponseDto2 displayDropdown();

//    //report
//    List<Integer> findDistinctYears();

    List<Object[]> findInjectionResultsByYear(Integer year);

    public List<Object[]> findCustomersVaccinatedByMonth(Integer year);

    public List<Object[]> findVaccineCountByMonth(Integer year);

}
