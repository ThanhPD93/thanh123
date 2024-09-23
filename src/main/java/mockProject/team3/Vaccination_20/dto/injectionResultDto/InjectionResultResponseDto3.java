package mockProject.team3.Vaccination_20.dto.injectionResultDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto3;
import mockProject.team3.Vaccination_20.dto.vaccineDto.VaccineResponseDto2;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InjectionResultResponseDto3 {
    private VaccineResponseDto2 vaccineFromInjectionResult;
    private String numberOfInjection;
    private LocalDate injectionDate;
    private LocalDate nextInjectionDate;
    private CustomerResponseDto3 customer;
}
