package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.InjectionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InjectionResultRepository extends JpaRepository<InjectionResult, String> {
}
