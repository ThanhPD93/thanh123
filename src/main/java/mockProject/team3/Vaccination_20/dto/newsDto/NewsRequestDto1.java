package mockProject.team3.Vaccination_20.dto.newsDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsRequestDto1 {

    @Size(max = 50, message = "Vaccine type ID must not exceed 50 characters!")
    @NotBlank(message = "Vaccine type ID must not be empty!")
    private String newsId;

    @Size(max = 50, message = "Vaccine type ID must not exceed 50 characters!")
    @NotBlank(message = "Vaccine type ID must not be empty!")
    private String content;

    @Size(max = 4000, message = "Customer ID must not exceed 4000 characters!")
    private String preview;

    @Size(max = 300, message = "Customer ID must not exceed 300 characters!")
    private String title;

}