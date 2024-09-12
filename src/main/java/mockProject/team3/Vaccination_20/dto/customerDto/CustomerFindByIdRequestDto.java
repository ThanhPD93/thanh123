package mockProject.team3.Vaccination_20.dto.customerDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerFindByIdRequestDto {
    @Size(max = 36, message = "Customer ID must not exceed 36 characters!")
    @NotBlank(message = "Customer ID must not be empty!")
    private String customerId;
}
