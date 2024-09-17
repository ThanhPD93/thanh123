package mockProject.team3.Vaccination_20.dto.injectionScheduleDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaveRequestInjectionSchedule {
    @NotBlank(message = "")
    private String injectionScheduleId;

    @Size(max = 1000, message = "")
    private String injectionScheduleDescription;

    @NotNull(message = "endDate must not be null")
    private LocalDate endDate;

    @NotNull(message = "place must not be null")
    private String place;

    @NotNull(message = "startDate must not be null")
    private LocalDate startDate;

    @NotNull(message = "startDate must not be null")
    private String vaccineName;
}
