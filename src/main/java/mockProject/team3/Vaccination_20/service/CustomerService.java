package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.customerDto.CustomerRequestDto1;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto1;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto2;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Page<CustomerResponseDto2> findByFullNameOrAddress(String searchInput, int page, int size);
    void deleteCustomers(List<String> customerIds);
    boolean addCustomer(CustomerRequestDto1 customerRequestDto1);
    CustomerResponseDto1 findById(String id);

}
