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

    private String customerId;

    private String fullName;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String address;

    private String identityCard;

    private String phone;
}
