package mockProject.team3.Vaccination_20.service.impl;

import mockProject.team3.Vaccination_20.dto.customerDto.CustomerRequestDto1;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto1;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto2;
import mockProject.team3.Vaccination_20.model.Customer;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.repository.InjectionScheduleRepository;
import mockProject.team3.Vaccination_20.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private InjectionScheduleRepository injectionScheduleRepository;

    @Override
    public Page<CustomerResponseDto2> findByFullNameOrAddress(String searchInput, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customers;
        if (searchInput.trim().isEmpty()) {
            customers = customerRepository.findAll(pageable);
        } else {
            customers = customerRepository.findBySearch(searchInput,pageable);
        }
        List<CustomerResponseDto2> responseCustomers = modelMapper.map(customers.getContent(), new TypeToken<List<CustomerResponseDto2>>(){}.getType());
        return new PageImpl<>(responseCustomers, pageable, customers.getTotalElements());
    }

    @Override
    public void deleteCustomers(List<String> customerIds) {
        if (customerIds == null || customerIds.isEmpty()) {
            throw new IllegalArgumentException("No customers selected for deletion");
        }
        customerRepository.deleteCustomersByIds(customerIds);
    }

    @Override
    public boolean addCustomer(CustomerRequestDto1 customerRequestDto1) {
        try {
            Customer customer = modelMapper.map(customerRequestDto1, Customer.class);
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customerRepository.save(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CustomerResponseDto1 findById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error at findByIdService"));
        return modelMapper.map(customer, CustomerResponseDto1.class);
    }



}
