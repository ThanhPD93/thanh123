package mockProject.team3.Vaccination_20.repository;

import jakarta.transaction.Transactional;
import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployee;
import mockProject.team3.Vaccination_20.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByUsername(String username);

    @Query("SELECT e FROM Employee e WHERE " +
            "LOWER(e.employeeName) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(e.address) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(e.email) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(e.position) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(e.username) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(e.workingPlace) LIKE LOWER(CONCAT('%', :searchInput, '%'))")
    Page<Employee> findBySearch(String searchInput, Pageable pageable);

    List<Employee> findByEmployeeNameContainsIgnoreCaseOrAddressContainsIgnoreCase(String employeeName, String address);

    List<LResponseEmployee> findAllBy();

    Employee findByEmployeeId(String employeeId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Employee e WHERE e.employeeId IN :ids")
    void deleteEmployeesByIds(@Param("ids") List<String> ids);

}