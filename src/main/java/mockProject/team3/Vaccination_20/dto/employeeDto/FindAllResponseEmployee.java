package mockProject.team3.Vaccination_20.dto.employeeDto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.utils.Gender;

import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindAllResponseEmployee {
    private String employeeId;
    private String employeeName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phone;
    private String address;
    private byte[] image;
}
