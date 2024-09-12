package mockProject.team3.Vaccination_20.repository;

import jakarta.transaction.Transactional;
import mockProject.team3.Vaccination_20.dto.forvaccine.VaccineDto;
import mockProject.team3.Vaccination_20.model.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, String> {

    @Query("SELECT v FROM Vaccine v WHERE "+
            "LOWER(v.vaccineId) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(v.vaccineName) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(v.vaccineType.vaccineTypeName) LIKE LOWER(CONCAT('%', :searchInput, '%'))")
    Page<Vaccine> findBySearchInput(String searchInput, Pageable pageable);
}
