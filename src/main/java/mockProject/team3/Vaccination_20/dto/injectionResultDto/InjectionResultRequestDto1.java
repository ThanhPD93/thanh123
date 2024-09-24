package mockProject.team3.Vaccination_20.dto.injectionResultDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InjectionResultRequestDto1 {
    @NotNull(message = "Please select a customer!")
    private String customerId;

    @NotNull(message = "Please select an vaccine type!")
    private String vaccineTypeId;

    @NotNull(message = "Please select an vaccine!")
    private String vaccineId;

    private long numberOfInjection;

    @NotNull(message = "Please select an injection date!")
    private LocalDate injectionDate;

    @NotNull(message = "Please select a next injection date!")
    private LocalDate nextInjectionDate;

    @NotNull(message = "Please select an injection place!")
    private String injectionPlace;
}
