package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
