package mockProject.team3.Vaccination_20.dto.customerDto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.utils.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRequestDto1 {
    @Size(max = 36, message = "Customer ID must not exceed 36 characters!")
    @NotBlank(message = "Customer ID must not be empty!")
    private String customerId;

    @NotBlank(message = "Customer address must not be empty!")
    private String address;

    @NotNull(message = "Customer date of birth must not be null!")
    private LocalDate dateOfBirth;

    @AssertTrue(message = "Date of birth must be in the past.")
    public boolean isDateOfBirthInThePast() {
        return dateOfBirth.isBefore(LocalDate.now());
    }

    @NotBlank(message = "Email must not be empty!")
    @Size(max = 100, message = "Email must not exceed 100 characters!")
    @Email(message = "Invalid email format!")
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
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    @NotBlank(message = "Phone number must not be empty!")
    @Size(max = 20, message = "Phone number must not exceed 20 characters!")
    @Pattern(regexp = "^\\+?[0-9]{7,20}$", message = "Invalid phone number format.")
    private String phone;

    @NotBlank(message = "Customer's username must not be empty!")
    private String username;
}
