package mockProject.team3.Vaccination_20.dto.customerDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.utils.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerAddRequestDto {
    @Size(max = 36, message = "Customer ID must not exceed 36 characters!")
    @NotBlank(message = "Customer ID must not be empty!")
    private String customerId;

    @NotBlank(message = "Customer address must not be empty!")
    private String address;

    @NotNull(message = "Customer date of birth must not be null!")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Email must not be empty!")
    @Size(max = 100, message = "Email must not exceed 100 characters!")
    private String email;

    @NotBlank(message = "Customer's full name must not be empty!")
    @Size(max = 100, message = "Customer's full name must not exceed 100 characters!")
    private String fullName;

    @NotNull(message = "Please select a gender for the customer!")
    private Gender gender;

    @NotBlank(message = "Customer must have an ID card!")
    @Size(max = 12, message = "Customer's ID card must not exceed 12 characters!")
    private String identityCard;

    @NotBlank(message = "Password must not be blank!")
    private String password;

    @NotBlank(message = "Phone number must not be empty!")
    @Size(max = 20, message = "Phone number must not exceed 20 characters!")
    private String phone;
    
    @NotBlank(message = "Customer's username must not be empty!")
    private String username;
}
