package mockProject.team3.Vaccination_20.dto.customerDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRequestDto2 {
    @NotEmpty(message = "list of customer ids must not be null or empty!")
	private List<String> customerIds;
}
