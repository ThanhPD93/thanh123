package mockProject.team3.Vaccination_20.service;


import mockProject.team3.Vaccination_20.dto.injectionresult.CInjectionResultDTO;
import mockProject.team3.Vaccination_20.dto.injectionresult.InjectionResultDTO;
import mockProject.team3.Vaccination_20.dto.injectionresult.UInjectionResultDTO;
import mockProject.team3.Vaccination_20.dto.report.InjectionResultStats;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import org.springframework.data.domain.Page;

import java.util.List;


public interface InjectionResultService {
    //show with pagination
    public Page<InjectionResultDTO> findBySearchWithPagination(String searchInput, int page, int size);

    //list all
    List<InjectionResultDTO> getAllInjectionResults();

   //get data to dropdown
    List<String> getAllInjectionPlaces();

    //add IR
    public InjectionResult addInjectionResult(CInjectionResultDTO dto);

    //detail
    public UInjectionResultDTO getInjectionResultById(String id);

    //update
    UInjectionResultDTO updateInjectionResult(String injectionResultId, UInjectionResultDTO uInjectionResultDTO);

    //delete
    public void deleteInjectionResults(List<String> ids);

    //report
    public List<InjectionResultStats> getInjectionResultsByYear(int year);


}
