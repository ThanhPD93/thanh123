package mockProject.team3.Vaccination_20.dto.injectionResultDto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InjectionResultResponseDto1 {
    private String injectionResultId;
    private String customerInfo; // customerId - fullName - dateOfBirth
    private String vaccineName;
    private String vaccineTypeName;//
    private long numberOfInjection;
    private LocalDate injectionDate;
    private LocalDate nextInjectionDate;
}
