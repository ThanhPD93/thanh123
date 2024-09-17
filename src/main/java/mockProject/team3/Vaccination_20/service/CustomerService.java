package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.customerDto.CustomerAddRequestDto;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerFindByIdDto;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerFindByIdRequestDto;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerListResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

import mockProject.team3.Vaccination_20.model.Customer;

import java.util.List;

public interface CustomerService {
    Page<CustomerListResponseDto> findByFullNameOrAddress(String searchInput, int page, int size);
    void deleteCustomers(List<String> customerIds);
    boolean addCustomer(CustomerAddRequestDto customerAddRequestDto);
    CustomerFindByIdDto findById(String id);


    //for add-ir
    public List<Customer> getAllCustomers();
    Customer findByIdIR(String customerId);
}
