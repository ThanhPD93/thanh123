package mockProject.team3.Vaccination_20.dto.vaccineTypeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.utils.Status;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindAllResponseVaccineType {
    private String vaccineTypeId;
    private String vaccineTypeName;
    private String vaccineTypeDescription;
    private Status status;
}
