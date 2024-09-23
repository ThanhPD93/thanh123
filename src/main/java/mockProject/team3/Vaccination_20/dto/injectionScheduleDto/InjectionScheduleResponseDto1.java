package mockProject.team3.Vaccination_20.dto.injectionScheduleDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto4;
import mockProject.team3.Vaccination_20.utils.InjectionScheduleStatus;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InjectionScheduleResponseDto1 {
    private String injectionScheduleId;
    private String injectionScheduleDescription;
    private LocalDate endDate;
    private String place;
    private LocalDate startDate;
    private InjectionScheduleStatus status;
    private VaccineResponseDto4 vaccineFromInjectionSchedule;
}
