package mockProject.team3.Vaccination_20.dto.customerDto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import mockProject.team3.Vaccination_20.utils.Gender;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerFindByIdDto {
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
