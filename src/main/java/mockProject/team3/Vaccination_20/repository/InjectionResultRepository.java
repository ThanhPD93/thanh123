package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.dto.injectionResultDto.InjectionResultResponseDto1;
import mockProject.team3.Vaccination_20.model.InjectionResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InjectionResultRepository extends JpaRepository<InjectionResult, String> {

    @Query("SELECT new mockProject.team3.Vaccination_20.dto.injectionResultDto.InjectionResultResponseDto1("+
            "ir.injectionResultId," +
            "CONCAT(c.customerId, ' - ', c.fullName, ' - ', c.dateOfBirth), " +
            "v.vaccineName, ir.prevention, ir.numberOfInjection, ir.injectionDate, ir.nextInjectionDate) " +
            "FROM InjectionResult ir " +
            "JOIN ir.customer c " +
            "JOIN ir.vaccineFromInjectionResult v")
    List<InjectionResultResponseDto1> findAllInjectionResults();


    @Query("SELECT new mockProject.team3.Vaccination_20.dto.injectionResultDto.InjectionResultResponseDto1(" +
            "ir.injectionResultId," +
            "CONCAT(c.customerId, ' - ', c.fullName, ' - ', c.dateOfBirth), " +
            "v.vaccineName, vt.vaccineTypeName, ir.numberOfInjection, ir.injectionDate, ir.nextInjectionDate) " +
            "FROM InjectionResult ir " +
            "JOIN ir.customer c " +
            "JOIN ir.vaccineFromInjectionResult v " +
            "JOIN v.vaccineType vt " +
            "WHERE c.fullName LIKE %:searchInput% OR c.customerId LIKE %:searchInput%")
    Page<InjectionResultResponseDto1> findAllWithPagination(@Param("searchInput") String searchInput, Pageable pageable);

	//get year for report
    @Query("SELECT DISTINCT YEAR(ir.injectionDate) FROM InjectionResult ir ORDER BY YEAR(ir.injectionDate)")
    List<Integer> findYears();

    @Query("SELECT ir FROM InjectionResult ir " +
            "JOIN ir.customer c " +
            "JOIN ir.vaccineFromInjectionResult v " +
            "WHERE LOWER(v.vaccineName) LIKE LOWER(CONCAT('%', :searchInput, '%')) " +
            "OR LOWER(ir.prevention) LIKE LOWER(CONCAT('%', :searchInput, '%')) " +
            "OR CAST(ir.numberOfInjection AS string) LIKE %:searchInput% " +
            "OR LOWER(c.fullName) LIKE LOWER(CONCAT('%', :searchInput, '%')) " +
            "OR LOWER(c.identityCard) LIKE LOWER(CONCAT('%', :searchInput, '%')) " +
            "OR LOWER(c.phone) LIKE LOWER(CONCAT('%', :searchInput, '%')) " +
            "OR FUNCTION('DATE_FORMAT', ir.injectionDate, '%Y-%m-%d') = :searchInput " +
            "OR FUNCTION('DATE_FORMAT', ir.nextInjectionDate, '%Y-%m-%d') = :searchInput")
    Page<InjectionResult> findBySearch(@Param("searchInput") String searchInput, Pageable pageable);



    //report injection
    @Query("SELECT MONTH(ir.injectionDate) AS month, COUNT(ir) AS total "
            + "FROM InjectionResult ir WHERE YEAR(ir.injectionDate) = :year "
            + "GROUP BY MONTH(ir.injectionDate) ORDER BY MONTH(ir.injectionDate)")
    List<Object[]> findInjectionResultsByYear(@Param("year") Integer year);

    //report customer
    @Query("SELECT MONTH(ir.injectionDate) AS month, COUNT(DISTINCT c.customerId) AS count " +
            "FROM InjectionResult ir " +
            "JOIN ir.customer c " +
            "WHERE YEAR(ir.injectionDate) = :year " +
            "GROUP BY MONTH(ir.injectionDate) " +
            "ORDER BY MONTH(ir.injectionDate)")
    List<Object[]> findCustomersVaccinatedByMonth(Integer year);

    //report vaccine
    @Query("SELECT MONTH(ir.injectionDate) AS month, COUNT(DISTINCT v.vaccineId) AS count " +
            "FROM InjectionResult ir " +
            "JOIN ir.vaccineFromInjectionResult v " +
            "WHERE YEAR(ir.injectionDate) = :year " +
            "GROUP BY MONTH(ir.injectionDate) " +
            "ORDER BY MONTH(ir.injectionDate)")
    List<Object[]> findVaccineCountByMonth(Integer year);

    @Query("SELECT ir FROM InjectionResult ir " +
            "JOIN ir.customer c " +
            "JOIN ir.vaccineFromInjectionResult v " +
            "JOIN v.vaccineType vt " +
            "WHERE (:startDate IS NULL OR ir.injectionDate >= :startDate) " +
            "AND (:endDate IS NULL OR ir.injectionDate <= :endDate) " +
            "AND (:vaccineTypeName IS NULL OR vt.vaccineTypeName = :vaccineTypeName) " +
            "AND (:vaccineName IS NULL OR v.vaccineName = :vaccineName)")
    Page<InjectionResult> filterInjectionResults(@Param("startDate") LocalDate startDate,
                                                 @Param("endDate") LocalDate endDate,
                                                 @Param("vaccineTypeName") String vaccineTypeName,
                                                 @Param("vaccineName") String vaccineName,
                                                 Pageable pageable);



}
