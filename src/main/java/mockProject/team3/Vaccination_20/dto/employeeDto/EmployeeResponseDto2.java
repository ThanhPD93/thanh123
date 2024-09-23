package mockProject.team3.Vaccination_20.dto.employeeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.utils.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeResponseDto2 {
    private String employeeId;
    private String employeeName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phone;
    private String address;
    private byte[] image;
}
