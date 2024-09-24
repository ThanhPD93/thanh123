package mockProject.team3.Vaccination_20.dto.injectionResultDto;

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
    private long numberOfInjection;
    private LocalDate injectionDate;
    private LocalDate nextInjectionDate;
    private String injectionPlace;

}
