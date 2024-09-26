package mockProject.team3.Vaccination_20.dto.newsDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsDeleteDto {
    private List<String> ids;
}