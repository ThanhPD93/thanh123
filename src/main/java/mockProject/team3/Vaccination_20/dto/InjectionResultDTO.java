package mockProject.team3.Vaccination_20.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InjectionResultDTO {
    private String customerInfo; // customerId - fullName - dateOfBirth
    private String vaccineName;
    private String vaccineTypeName;//
    private String numberOfInjection;
    private LocalDate injectionDate;
    private LocalDate nextInjectionDate;
}
