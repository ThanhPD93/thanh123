package mockProject.team3.Vaccination_20.dto.customerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.utils.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerResponseDto1 {
    private String customerId;

    private String address;

    private LocalDate dateOfBirth;

    private String email;

    private String fullName;

    private Gender gender;

    private String identityCard;

    private String password;

    private String phone;

    private String username;

}
