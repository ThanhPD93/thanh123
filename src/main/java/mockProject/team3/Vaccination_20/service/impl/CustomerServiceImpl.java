package mockProject.team3.Vaccination_20.service.impl;

import mockProject.team3.Vaccination_20.model.Customer;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(String customerId) {
        return customerRepository.findCustomerByCustomerId(customerId);
    }
}
