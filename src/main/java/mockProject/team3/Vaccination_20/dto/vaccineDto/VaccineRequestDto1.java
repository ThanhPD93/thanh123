package mockProject.team3.Vaccination_20.dto.vaccineDto;

import lombok.*;
import mockProject.team3.Vaccination_20.utils.Status;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccineRequestDto1 {
    private String vaccineId;
    private String contraindication;
    private String indication;
    private int numberOfInjection;
    private String vaccineOrigin;
    private LocalDate timeBeginNextInjection;
    private LocalDate timeEndNextInjection;
    private String vaccineUsage;
    private String vaccineName;
    private Status vaccineStatus;
    private String vaccineType;
}
