package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.injectionResultDto.*;
import mockProject.team3.Vaccination_20.dto.report.ReportInjectionResultDto;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;


public interface InjectionResultService {

    List<String> getAllInjectionPlaces();
    int addInjectionResult(InjectionResultRequestDto1 dto);
    InjectionResultResponseDto3 getInjectionResultById(String id);
    UInjectionResultDTO updateInjectionResult(String injectionResultId, UInjectionResultDTO uInjectionResultDTO);
    void deleteInjectionResults(List<String> ids);
    Page<InjectionResultResponseDto3> findBySearch(String searchInput, int page, int size);
	InjectionResultResponseDto2 displayDropdown();

    //report
    List<Integer> findDistinctYears();

    List<Object[]> findInjectionResultsByYear(Integer year);

    public List<Object[]> findCustomersVaccinatedByMonth(Integer year);

    public List<Object[]> findVaccineCountByMonth(Integer year);

    // report list
    Page<ReportInjectionResultDto> filterReportInjection(LocalDate startDate, LocalDate endDate, String vaccineTypeName, String vaccineName, int page, int size);

}
