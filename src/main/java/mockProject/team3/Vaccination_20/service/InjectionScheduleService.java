package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.InjectionScheduleDto;
import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.SaveRequestInjectionSchedule;
import mockProject.team3.Vaccination_20.model.InjectionSchedule;
import mockProject.team3.Vaccination_20.repository.InjectionScheduleRepository;

import java.util.List;

public interface InjectionScheduleService {
    List<InjectionScheduleDto> findAll();
    int save(SaveRequestInjectionSchedule saveRequestInjectionSchedule);
}
