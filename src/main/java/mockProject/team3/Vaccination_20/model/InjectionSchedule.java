package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.*;
import lombok.*;
import mockProject.team3.Vaccination_20.utils.InjectionScheduleStatus;
import mockProject.team3.Vaccination_20.utils.InjectionScheduleIdGenerator;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InjectionSchedule {

    @Id
    @GeneratedValue(generator = "injection_schedule_id_generator")
    @GenericGenerator(
        name = "injection_schedule_id_generator",
        strategy = "mockProject.team3.Vaccination_20.utils.InjectionScheduleIdGenerator"
    )
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
