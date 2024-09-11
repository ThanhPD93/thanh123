package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.VaccineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineTypeRepository extends JpaRepository<VaccineType, String> {

    //use for add-ir
	List<VaccineType> findAll();
}
