package mockProject.team3.Vaccination_20.dto.forvaccine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class VaccineTypeDtoForVaccine {
    private String vaccineTypeId;
    private String vaccineTypeDescription;
    private String vaccineTypeName;

    public VaccineTypeDtoForVaccine() {
    }

    public VaccineTypeDtoForVaccine(String vaccineTypeId, String vaccineTypeName, String vaccineTypeDescription) {
        this.vaccineTypeId = vaccineTypeId;
        this.vaccineTypeName = vaccineTypeName;
        this.vaccineTypeDescription = vaccineTypeDescription;
    }

    public String getVaccineTypeDescription() {
        return vaccineTypeDescription;
    }

    public String getVaccineTypeName() {
        return vaccineTypeName;
    }

    public String getVaccineTypeId() {
        return vaccineTypeId;
    }

    public void setVaccineTypeDescription(String vaccineTypeDescription) {
        this.vaccineTypeDescription = vaccineTypeDescription;
    }

    public void setVaccineTypeId(String vaccineTypeId) {
        this.vaccineTypeId = vaccineTypeId;
    }

    public void setVaccineTypeName(String vaccineTypeName) {
        this.vaccineTypeName = vaccineTypeName;
    }
}
