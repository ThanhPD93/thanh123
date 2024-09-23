package mockProject.team3.Vaccination_20.dto.vaccineTypeDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VaccineTypeRequestDto2 {
        private List<String> vaccineTypeListIds;
}
