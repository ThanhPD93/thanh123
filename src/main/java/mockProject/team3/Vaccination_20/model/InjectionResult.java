package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.*;
import lombok.*;
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
    @Column(length = 36)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String injectionResultId;

    private LocalDate injectionDate;

    private String injectionPlace;

    private LocalDate nextInjectionDate;

    @Column(length = 100)
    private String numberOfInjection;

    @Column(length = 100)
    private String prevention;

    //relationship
    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Vaccine vaccineFromInjectionResult;


}
