package mockProject.team3.Vaccination_20.dto.injectionScheduleDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccineDto {
    @NotBlank(message = "vaccineName from vaccineFromInjectionSchedule must not be null")
    @Size(max = 1000, message = "vaccine name must not be over 1000 characters")
    private String vaccineName;
}
