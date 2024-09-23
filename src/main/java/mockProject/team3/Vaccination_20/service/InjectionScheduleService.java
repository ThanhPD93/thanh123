package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.InjectionScheduleResponseDto1;
import mockProject.team3.Vaccination_20.dto.injectionScheduleDto.InjectionScheduleRequestDto1;
import org.springframework.data.domain.Page;

public interface InjectionScheduleService {
    Page<InjectionScheduleResponseDto1> findBySearch(String searchInput, int page, int size);
    int save(InjectionScheduleRequestDto1 injectionScheduleRequestDto1);
    InjectionScheduleResponseDto1 findByInjectionScheduleId(String id);
}
