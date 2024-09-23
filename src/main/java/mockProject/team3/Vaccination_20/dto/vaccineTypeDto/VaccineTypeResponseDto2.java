package mockProject.team3.Vaccination_20.dto.vaccineTypeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.utils.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineTypeResponseDto2 {
    private String vaccineTypeId;
    private String vaccineTypeName;
    private String vaccineTypeDescription;
    private Status vaccineTypeStatus;
    private byte[] vaccineTypeImage;
}