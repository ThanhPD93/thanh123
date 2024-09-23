package mockProject.team3.Vaccination_20.dto.injectionResultDto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InjectionResultRequestDto1 {
    private String customerId;
    private String vaccineTypeId;
    private String vaccineId;
    private String injection;
    private LocalDate injectionDate;
    private LocalDate nextInjectionDate;
    private String injectionPlace;
}
