package mockProject.team3.Vaccination_20.dto.response.forlist;

import mockProject.team3.Vaccination_20.utils.Gender;

import java.time.LocalDate;

public interface LResponseEmployee {
    String getEmployeeId();
    String getAddress();
    LocalDate getDateOfBirth();
    String getEmployeeName();
    Gender getGender();
    String getImage();
    String getPhone();
}
