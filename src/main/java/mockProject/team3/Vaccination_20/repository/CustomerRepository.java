package mockProject.team3.Vaccination_20.repository;

import jakarta.transaction.Transactional;
import mockProject.team3.Vaccination_20.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("SELECT c FROM Customer c WHERE " +
            "LOWER(c.fullName) LIKE LOWER(CONCAT('%', :searchInput, '%')) OR " +
            "LOWER(c.address) LIKE LOWER(CONCAT('%', :searchInput, '%'))")
    Page<Customer> findBySearch(String searchInput, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Customer c WHERE c.customerId IN :ids")
    void deleteCustomersByIds(@Param("ids") List<String> ids);

    @Query("SELECT c FROM Customer c "
            + "JOIN FETCH c.injectionResults ir "
            + "JOIN FETCH ir.vaccineFromInjectionResult v "
            + "WHERE c.customerId = :customerId")
    Customer findCustomerWithInjectionResultsAndVaccines(String customerId);

    //use for add-ir
    List<Customer> findAll();
    Customer findCustomerByCustomerId(String customerId);
}
