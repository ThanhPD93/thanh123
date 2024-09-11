package mockProject.team3.Vaccination_20.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CInjectionResultDTO {
    private String customerId;
    private String vaccineTypeName;
	private String vaccineName;
    private String injection;
    private LocalDate injectionDate;
    private LocalDate nextInjectionDate;
    private String injectionPlace;
}
