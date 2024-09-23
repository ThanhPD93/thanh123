package mockProject.team3.Vaccination_20.dto.vaccineTypeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto1;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccineTypeResponseDto3 {
	private String vaccineTypeId;
    private String vaccineTypeName;
    private List<VaccineResponseDto1> vaccines;
}
