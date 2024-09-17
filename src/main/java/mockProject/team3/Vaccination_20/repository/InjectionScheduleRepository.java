package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.InjectionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InjectionScheduleRepository extends JpaRepository<InjectionSchedule, String> {
    List<InjectionSchedule> findAll();
}
