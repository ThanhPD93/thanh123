package mockProject.team3.Vaccination_20.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChartData {
    private List<String> months;
    private List<Integer> results;
}
