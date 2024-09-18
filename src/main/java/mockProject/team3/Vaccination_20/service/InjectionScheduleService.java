package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.InjectionScheduleDto;
import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.SaveRequestInjectionSchedule;
import mockProject.team3.Vaccination_20.model.InjectionSchedule;
import mockProject.team3.Vaccination_20.repository.InjectionScheduleRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InjectionScheduleService {
    Page<InjectionScheduleDto> findBySearch(String searchInput, int page, int size);
    int save(SaveRequestInjectionSchedule saveRequestInjectionSchedule);
}
