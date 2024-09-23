package mockProject.team3.Vaccination_20.dto.customerDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerResponseDto3 {
    private String customerId;
    private LocalDate dateOfBirth;
    private String fullName;
}
