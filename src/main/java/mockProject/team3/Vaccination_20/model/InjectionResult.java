package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InjectionResult {
	@Id
    @Column(length = 36)
    private String injectionResultId;

    private LocalDate injectionDate;

    private String injectionPlace;

    private LocalDate nextInjectionDate;

    @Column(length = 100)
    private String numberOfInjection;

    @Column(length = 100)
    private String prevention;

    //relationship
    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Vaccine vaccineFromInjectionResult;

}
