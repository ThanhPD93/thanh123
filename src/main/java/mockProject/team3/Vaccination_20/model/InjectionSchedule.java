package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InjectionSchedule {
	@Id
    @Column(length = 36)
    private String injectionScheduleId;

    @Column(length = 1000)
    private String injectionScheduleDescription;

    private LocalDate endDate;

    private String place;

    private LocalDate startDate;

    //relationship
    @ManyToOne
    private Vaccine vaccineFromInjectionSchedule;
}
