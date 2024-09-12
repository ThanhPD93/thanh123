package mockProject.team3.Vaccination_20.dto.customerDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.utils.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerListResponseDto {

    @Size(max = 36, message = "Customer ID must not exceed 36 characters!")
    @NotBlank(message = "Customer ID must not be empty!")
    private String customerId;

    @NotBlank(message = "Full name cannot be blank")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "Identity card cannot be blank")
    @Size(max = 12, message = "Identity card cannot exceed 12 characters")
    private String identityCard;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\d{10,20}", message = "Phone number must be between 10 and 20 digits")
    private String phone;
}
