package mockProject.team3.Vaccination_20.dto.customerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerListForReportDto {

    private String customerId;

    private String fullName;

    private LocalDate dateOfBirth;

    private String address;

    private String identityCard;

    private Long totalNumberOfInjection;
}
