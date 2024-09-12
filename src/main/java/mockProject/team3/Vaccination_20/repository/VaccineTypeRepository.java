package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.VaccineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccineTypeRepository extends JpaRepository<VaccineType, String> {
    VaccineType findByVaccineTypeId(String vaccineTypeId);

    @Query("SELECT vt FROM VaccineType vt WHERE " +
            "LOWER(vt.vaccineTypeId) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(vt.vaccineTypeName) LIKE LOWER(CONCAT('%', :searchInput, '%')) ")
//    @Query("SELECT vt FROM VaccineType vt WHERE " +
//            "LOWER(vt.vaccineTypeId) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
//            "LOWER(vt.vaccineTypeName) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
//            "CAST(vt.status AS string) LIKE LOWER(CONCAT('%', :searchInput, '%'))")
    Page<VaccineType> findBySearch(String searchInput, Pageable pageable);


}
