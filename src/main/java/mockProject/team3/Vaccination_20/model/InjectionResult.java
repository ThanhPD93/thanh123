package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InjectionResult {
    @Id
    @GeneratedValue(generator = "injection_result_id")
    @GenericGenerator(name = "injection_result_id", strategy = "mockProject.team3.Vaccination_20.utils.InjectionResultIdGenerator")
    @Column(length = 36, nullable = false)
    private String injectionResultId;

    @Column(length = 36, nullable = false)
    private LocalDate injectionDate;

    private String injectionPlace;

    private LocalDate nextInjectionDate;

    private long numberOfInjection;

    @Column(length = 100)
    private String prevention;

    //relationship
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Vaccine vaccineFromInjectionResult;


}
