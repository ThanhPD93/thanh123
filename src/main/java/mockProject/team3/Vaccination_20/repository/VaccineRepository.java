package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, String> {

    //use for add-ir
    List<Vaccine> findAll();
}
