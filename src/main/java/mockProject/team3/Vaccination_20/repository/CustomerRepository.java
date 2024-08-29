package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
