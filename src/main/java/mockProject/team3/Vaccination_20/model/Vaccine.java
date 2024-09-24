package mockProject.team3.Vaccination_20.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mockProject.team3.Vaccination_20.utils.InjectionScheduleStatus;
import mockProject.team3.Vaccination_20.utils.Status;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vaccine {
    @Id
    @Column(length = 36)
    private String vaccineId;

    @Column(length = 200)
    private String contraindication;

    @Column(length = 200)
    private String indication;

    private int numberOfInjection;

    @Column(length = 50)
    private String vaccineOrigin;

    private LocalDate timeBeginNextInjection;

    private LocalDate timeEndNextInjection;

    @Column(length = 200)
    private String vaccineUsage;

    @Column(length = 100)
    private String vaccineName;

    private Status vaccineStatus;

    //relationship
    @OneToMany(mappedBy = "vaccineFromInjectionResult")
    private List<InjectionResult> injectionResults;

    @ManyToOne
    private VaccineType vaccineType;

    @OneToMany(mappedBy = "vaccineFromInjectionSchedule")
    private List<InjectionSchedule> injectionSchedule;
}
