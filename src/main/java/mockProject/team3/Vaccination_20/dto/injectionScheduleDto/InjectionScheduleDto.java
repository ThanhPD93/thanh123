package mockProject.team3.Vaccination_20.dto.injectionScheduleDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.utils.InjectionScheduleStatus;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InjectionScheduleDto {

    @Size(max = 1000, message = "id must be within 1000 characters")
    private String injectionScheduleDescription;

    @NotNull(message = "endDate must not be null")
    private LocalDate endDate;

    @NotNull(message = "place must not be null")
    private String place;

    @NotNull(message = "startDate must not be null")
    private LocalDate startDate;

    @NotNull(message = "status must not be null")
    private InjectionScheduleStatus status;

    @NotNull(message = "startDate must not be null")
    private VaccineDto vaccineFromInjectionSchedule;
}
