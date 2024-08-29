package mockProject.team3.Vaccination_20.dto.request.forupdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mockProject.team3.Vaccination_20.utils.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class URequestEmployee {
    private String employeeId;

    private String address;

    private LocalDate dateOfBirth;

    private String email;

    private String employeeName;

    private Gender gender;

    private String image;

    private String phone;

    private String position;

    private String workingPlace;
}
