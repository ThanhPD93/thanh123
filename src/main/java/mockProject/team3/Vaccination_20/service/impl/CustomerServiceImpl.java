package mockProject.team3.Vaccination_20.service.impl;

import mockProject.team3.Vaccination_20.dto.customerDto.CustomerAddRequestDto;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerFindByIdDto;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerFindByIdRequestDto;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerListResponseDto;
import mockProject.team3.Vaccination_20.dto.employeeDto.FindAllResponseEmployee;
import mockProject.team3.Vaccination_20.model.Customer;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.model.Customer;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<CustomerListResponseDto> findByFullNameOrAddress(String searchInput, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customers;
        if (searchInput.trim().isEmpty()) {
            customers = customerRepository.findAll(pageable);
        } else {
            customers = customerRepository.findBySearch(searchInput,pageable);
        }
        List<CustomerListResponseDto> responseCustomers = modelMapper.map(customers.getContent(), new TypeToken<List<CustomerListResponseDto>>(){}.getType());
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
    public boolean addCustomer(CustomerAddRequestDto customerAddRequestDto) {
        try {
            Customer customer = modelMapper.map(customerAddRequestDto, Customer.class);
            customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            customerRepository.save(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CustomerFindByIdDto findById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error at findByIdService"));
        return modelMapper.map(customer, CustomerFindByIdDto.class);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findByIdIR(String customerId) {
        return customerRepository.findCustomerByCustomerId(customerId);
    }
}
