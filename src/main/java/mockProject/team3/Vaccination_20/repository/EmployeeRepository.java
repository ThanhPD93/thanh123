package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByUsername(String username);
}
