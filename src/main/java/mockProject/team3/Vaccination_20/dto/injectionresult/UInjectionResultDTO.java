package mockProject.team3.Vaccination_20.dto.injectionresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UInjectionResultDTO {
    private String injectionResultId;
    private String customerId;
    private String vaccineTypeId;
    private String vaccineId;
    private String injection;
    private LocalDate injectionDate;
    private LocalDate nextInjectionDate;
    private String injectionPlace;

}
