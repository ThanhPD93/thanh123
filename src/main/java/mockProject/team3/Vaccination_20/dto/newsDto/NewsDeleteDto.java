package mockProject.team3.Vaccination_20.dto.newsDto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsDeleteDto {
    @NotEmpty(message = "list of news ids must not be null or empty!")
    private List<String> ids;
}