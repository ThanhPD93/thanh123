package mockProject.team3.Vaccination_20.dto.vaccineDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeResponseDto4;
import mockProject.team3.Vaccination_20.utils.Status;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccineResponseDto3 {
	private String vaccineId;
    private String vaccineName;
    private VaccineTypeResponseDto4 vaccineType;
    private int numberOfInjection;
    private String vaccineOrigin;
    private Status vaccineStatus;
}
