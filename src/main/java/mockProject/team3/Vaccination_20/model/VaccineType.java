package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockProject.team3.Vaccination_20.utils.Status;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VaccineType {
    @Id
    @Column(length = 36, unique = true, nullable = false)
    private String vaccineTypeId;

    @Column(length = 200, nullable = false)
    private String vaccineTypeDescription;

    @Column(length = 50, nullable = false)
    private String vaccineTypeName;

    @Column(length = 50, nullable = false)
    private Status vaccineTypeStatus;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] vaccineTypeImage;

    //relationship
    @Column(nullable = false)
    @OneToMany(mappedBy = "vaccineType")
    private List<Vaccine> vaccines;
}
