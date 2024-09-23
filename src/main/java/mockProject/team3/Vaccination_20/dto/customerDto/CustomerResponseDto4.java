package mockProject.team3.Vaccination_20.dto.customerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto4 {
    private String customerInfoId;
    private String customerInfoFullName;
    private LocalDate customerInfoDateOfBirth;
}
