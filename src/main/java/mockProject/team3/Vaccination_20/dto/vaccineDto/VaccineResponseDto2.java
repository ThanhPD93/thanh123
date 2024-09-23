package mockProject.team3.Vaccination_20.dto.vaccineDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeResponseDto4;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VaccineResponseDto2 {
    private String vaccineName;
    private VaccineTypeResponseDto4 vaccineType;
}
