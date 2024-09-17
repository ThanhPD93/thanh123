package mockProject.team3.Vaccination_20.dto.vaccineTypeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CRequestVaccineType {

    private String vaccineTypeId;

    private String vaccineTypeName;

    private String vaccineTypeDescription;

    private String vaccineTypeImage;

    private String vaccineTypeStatus;
}
