package mockProject.team3.Vaccination_20.dto.vaccineDto;

import jakarta.validation.constraints.*;
import lombok.*;
import mockProject.team3.Vaccination_20.utils.Status;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccineRequestDto1 {
    @Size(max = 36, message = "Vaccine ID must not exceed 36 characters!")
    private String vaccineId;

    @Size(max = 500, message = "Contraindication must not exceed 500 characters!")
    private String contraindication;

    @Size(max = 500, message = "Indication must not exceed 500 characters!")
    private String indication;

    @Min(value = 1, message = "Number of injections must be at least 1.")
    private int numberOfInjection;

    @NotBlank(message = "Vaccine origin must not be empty!")
    @Size(max = 100, message = "Vaccine origin must not exceed 100 characters!")
    private String vaccineOrigin;

    @NotNull(message = "Begin time for the next injection must not be null.")
    private LocalDate timeBeginNextInjection;

    @NotNull(message = "End time for the next injection must not be null.")
    private LocalDate timeEndNextInjection;

    @AssertTrue(message = "End time must be after the begin time for the next injection.")
    public boolean isEndTimeAfterBeginTime() {
        return timeEndNextInjection.isAfter(timeBeginNextInjection);
    }

    @NotBlank(message = "Vaccine usage must not be empty!")
    @Size(max = 200, message = "Vaccine usage must not exceed 200 characters!")
    private String vaccineUsage;

    @NotBlank(message = "Vaccine name must not be empty!")
    @Size(max = 100, message = "Vaccine name must not exceed 100 characters!")
    private String vaccineName;

    @NotNull(message = "Vaccine status must not be null.")
    private Status vaccineStatus;

    @NotBlank(message = "Vaccine type must not be empty!")
    @Size(max = 50, message = "Vaccine type must not exceed 50 characters!")
    private String vaccineTypeId;
}
