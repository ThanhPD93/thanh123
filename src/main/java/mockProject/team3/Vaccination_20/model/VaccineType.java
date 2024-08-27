package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VaccineType {
    @Id
    @Column(length = 36)
    private String vaccineTypeId;

    @Column(length = 200)
    private String vaccineTypeDescription;

    @Column(length = 50)
    private String vaccineTypeName;

    //relationship
    @OneToMany(mappedBy = "vaccineType")
    private List<Vaccine> vaccines;
}
