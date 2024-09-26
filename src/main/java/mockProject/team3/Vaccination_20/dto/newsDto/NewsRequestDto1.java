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

    @Size(max = 36, message = "News ID must not exceed 36 characters!")
    private String newsId;

    @Size(max = 4000, message = "Content must not exceed 4000 characters!")
    private String content;

    @Size(max = 4000, message = "Preview must not exceed 4000 characters!")
    private String preview;

    @Size(max = 300, message = "Title must not exceed 300 characters!")
    private String title;

}