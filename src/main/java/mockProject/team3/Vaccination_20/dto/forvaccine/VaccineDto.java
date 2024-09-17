package mockProject.team3.Vaccination_20.dto.forvaccine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockProject.team3.Vaccination_20.utils.InjectionScheduleStatus;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VaccineDto {
    private String vaccineId;
    private String contraindication;
    private String indication;
    private int numberOfInjection;
    private String vaccineOrigin;
    private LocalDate timeBeginNextInjection;
    private LocalDate timeEndNextInjection;
    private String vaccineUsage;
    private String vaccineName;
    private InjectionScheduleStatus vaccineStatus;
    private VaccineTypeDtoForVaccine vaccineType;
}
