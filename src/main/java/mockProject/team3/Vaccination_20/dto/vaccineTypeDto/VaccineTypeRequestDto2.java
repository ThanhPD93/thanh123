package mockProject.team3.Vaccination_20.dto.vaccineTypeDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccineTypeRequestDto2 {
        @NotEmpty(message = "list of vaccine type ids must not be null or empty!")
		private List<String> vaccineTypeListIds;
}
