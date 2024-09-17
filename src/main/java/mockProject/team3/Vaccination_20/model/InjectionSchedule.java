package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import mockProject.team3.Vaccination_20.utils.InjectionScheduleStatus;

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

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String place;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private InjectionScheduleStatus status;

    @ManyToOne
    private Vaccine vaccineFromInjectionSchedule;
}


