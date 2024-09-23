package mockProject.team3.Vaccination_20.dto.vaccineTypeDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.utils.Status;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccineTypeRequestDto1 {

    @Size(max = 50, message = "Vaccine type ID must not exceed 50 characters!")
    @NotBlank(message = "Vaccine type ID must not be empty!")
    private String vaccineTypeId;

    @Size(max = 50, message = "Vaccine type name must not exceed 50 characters!")
    @NotBlank(message = "Vaccine type name must not be empty!")
    private String vaccineTypeName;

    @Size(max = 200, message = "Vaccine type description must not exceed 200 characters!")
    @NotBlank(message = "Vaccine type description must not be empty!")
    private String vaccineTypeDescription;

    @NotNull(message = "Please select an image!")
    private String vaccineTypeImage;

    @NotNull(message = "Status must not be null!")
    private Status vaccineTypeStatus;
}
