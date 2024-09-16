package mockProject.team3.Vaccination_20.dto.injectionresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoDTO {
    private String customerInfoId;
    private String customerInfoFullName;
    private LocalDate customerInfoDateOfBirth;
}
