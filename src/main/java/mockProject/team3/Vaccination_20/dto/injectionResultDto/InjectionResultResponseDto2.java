package mockProject.team3.Vaccination_20.dto.injectionResultDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto3;
import mockProject.team3.Vaccination_20.dto.vaccineTypeDto.VaccineTypeResponseDto3;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InjectionResultResponseDto2 {
	private List<CustomerResponseDto3> customers;
    private List<VaccineTypeResponseDto3> vaccineTypes;
}
