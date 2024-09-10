package mockProject.team3.Vaccination_20.repository;

import mockProject.team3.Vaccination_20.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    //use for add-ir
    List<Customer> findAll();
}
