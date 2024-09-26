package mockProject.team3.Vaccination_20.dto.injectionResultDto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InjectionResultRequestDto1 {

    private String injectionResultId;

    @NotBlank(message = "Customer ID must not be empty!")
    @Size(max = 36, message = "Customer ID must not exceed 36 characters!")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Customer ID must be alphanumeric.")
    private String customerId;

    @NotNull(message = "Please select a vaccine type!")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Vaccine Type ID must be alphanumeric.")
    private String vaccineTypeId;

    @NotNull(message = "Please select a vaccine!")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Vaccine ID must be alphanumeric.")
    private String vaccineId;

    @Min(value = 1, message = "Number of injections must be at least 1.")
    @Max(value = 100, message = "Number of injections must not exceed 100.")
    private long numberOfInjection;

    @NotNull(message = "Please select an injection date!")
    @Past(message = "Injection date must be in the past or today.")
    private LocalDate injectionDate;

    @NotNull(message = "Please select a next injection date!")
    @Future(message = "Next injection date must be in the future.")
    private LocalDate nextInjectionDate;

    @NotNull(message = "Please select an injection place!")
    @Size(max = 255, message = "Injection place must not exceed 255 characters.")
    private String injectionPlace;
}
