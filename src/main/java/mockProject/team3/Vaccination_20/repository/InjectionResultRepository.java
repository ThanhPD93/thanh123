package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.dto.InjectionResultDTO;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InjectionResultRepository extends JpaRepository<InjectionResult, String> {

    @Query("SELECT new mockProject.team3.Vaccination_20.dto.InjectionResultDTO(" +
            "CONCAT(c.customerId, ' - ', c.fullName, ' - ', c.dateOfBirth), " +
            "v.vaccineName, ir.prevention, ir.numberOfInjection, ir.injectionDate, ir.nextInjectionDate) " +
            "FROM InjectionResult ir " +
            "JOIN ir.customer c " +
            "JOIN ir.vaccineFromInjectionResult v")
    List<InjectionResultDTO> findAllInjectionResults();

//    @Query("SELECT new mockProject.team3.Vaccination_20.dto.InjectionResultDTO(" +
//            "CONCAT(c.customerId, ' - ', c.fullName, ' - ', c.dateOfBirth), " +
//            "v.vaccineName, ir.prevention, ir.numberOfInjection, ir.injectionDate, ir.nextInjectionDate) " +
//            "FROM InjectionResult ir " +
//            "JOIN ir.customer c " +
//            "JOIN ir.vaccineFromInjectionResult v " +
//            "WHERE c.fullName LIKE %:searchInput% OR c.customerId LIKE %:searchInput%")
//    Page<InjectionResultDTO> findAllWithPagination(@Param("searchInput") String searchInput, Pageable pageable);

    @Query("SELECT new mockProject.team3.Vaccination_20.dto.InjectionResultDTO(" +
            "CONCAT(c.customerId, ' - ', c.fullName, ' - ', c.dateOfBirth), " +
            "v.vaccineName, vt.vaccineTypeName, ir.numberOfInjection, ir.injectionDate, ir.nextInjectionDate) " +
            "FROM InjectionResult ir " +
            "JOIN ir.customer c " +
            "JOIN ir.vaccineFromInjectionResult v " +
            "JOIN v.vaccineType vt " +
            "WHERE c.fullName LIKE %:searchInput% OR c.customerId LIKE %:searchInput%")
    Page<InjectionResultDTO> findAllWithPagination(@Param("searchInput") String searchInput, Pageable pageable);


//    @Query("SELECT DISTINCT i.prevention FROM InjectionResult i")
//    List<String> findAllPreventions();
//    @Query("SELECT DISTINCT i.injectionPlace FROM InjectionResult i")
//    List<String> findAllInjectionPlaces();

}
