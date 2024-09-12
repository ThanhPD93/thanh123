package mockProject.team3.Vaccination_20.dto.forvaccine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import mockProject.team3.Vaccination_20.model.InjectionSchedule;
import mockProject.team3.Vaccination_20.model.VaccineType;
import mockProject.team3.Vaccination_20.utils.VaccineStatus;

import java.time.LocalDate;
import java.util.List;

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
    private VaccineStatus status;
    private VaccineTypeDtoForVaccine vaccineType;
}
