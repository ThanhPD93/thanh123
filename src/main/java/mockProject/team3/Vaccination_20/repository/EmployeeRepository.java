package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    List<Employee> findBysearch(String searchInput);

    List<Employee> findByEmployeeNameContainsIgnoreCaseOrAddressContainsIgnoreCase(String employeeName, String address);

}
