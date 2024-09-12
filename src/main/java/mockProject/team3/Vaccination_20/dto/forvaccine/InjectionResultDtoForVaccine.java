package mockProject.team3.Vaccination_20.dto.forvaccine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InjectionResultDtoForVaccine {
    private String injectionResultId;
    private LocalDate injectionDate;
    private String injectionPlace;
    private LocalDate nextInjectionDate;
    private String numberOfInjection;
    private String prevention;
}
