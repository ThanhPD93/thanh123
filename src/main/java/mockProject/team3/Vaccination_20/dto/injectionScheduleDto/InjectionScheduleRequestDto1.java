package mockProject.team3.Vaccination_20.dto.injectionScheduleDto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InjectionScheduleRequestDto1 {

    @Size(max = 36, message = "injection schedule id must not exceed 36 characters!")
    private String injectionScheduleId;

    @Size(max = 1000, message = "Injection schedule description must not exceed 1000 characters!")
    private String injectionScheduleDescription;

    @NotNull(message = "endDate must not be null")
    private LocalDate endDate;

    @NotNull(message = "place must not be null")
    private String place;

    @NotNull(message = "startDate must not be null")
    private LocalDate startDate;

    @NotNull(message = "vaccineId must not be null")
    private String vaccineId;

    @AssertTrue(message = "Start date must be before end date.")
    public boolean isStartDateBeforeEndDate(){
        return startDate.isBefore(endDate);
    }
}
