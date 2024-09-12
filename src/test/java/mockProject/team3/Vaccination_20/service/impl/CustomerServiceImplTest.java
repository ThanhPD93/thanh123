package mockProject.team3.Vaccination_20.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import mockProject.team3.Vaccination_20.dto.customerDto.CustomerAddRequestDto;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerFindByIdDto;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerListResponseDto;
import mockProject.team3.Vaccination_20.model.Customer;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    public void testFindByFullNameOrAddress_EmptySearch() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customerPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(customerRepository.findAll(pageable)).thenReturn(customerPage);

        // Act
        Page<CustomerListResponseDto> result = customerService.findByFullNameOrAddress("", 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        verify(customerRepository).findAll(pageable); // Verify method call
    }

    @Test
    public void testFindByFullNameOrAddress_WithSearchInput() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Customer customer = new Customer();
        customer.setFullName("John Doe");

        Page<Customer> customerPage = new PageImpl<>(List.of(customer), pageable, 1);
        when(customerRepository.findBySearch("John", pageable)).thenReturn(customerPage);

        // Mocking the model mapper behavior
        CustomerListResponseDto dto = new CustomerListResponseDto();
        dto.setFullName("John Doe");
        when(modelMapper.map(anyList(), any(TypeToken.class))).thenReturn(List.of(dto));

        // Act
        Page<CustomerListResponseDto> result = customerService.findByFullNameOrAddress("John", 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("John Doe", result.getContent().get(0).getFullName());
        verify(customerRepository).findBySearch("John", pageable); // Verify method call
    }

    @Test
    public void testDeleteCustomers_Valid() {
        // Arrange
        List<String> customerIds = List.of("1", "2");

        // Act
        customerService.deleteCustomers(customerIds);

        // Assert
        verify(customerRepository).deleteCustomersByIds(customerIds); // Verify deletion
    }

    @Test
    public void testDeleteCustomers_EmptyList() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.deleteCustomers(Collections.emptyList());
        });

        assertEquals("No customers selected for deletion", exception.getMessage());
        verify(customerRepository, never()).deleteCustomersByIds(anyList()); // Verify delete is never called
    }

    @Test
    public void testAddCustomer_Success() {
        // Arrange
        CustomerAddRequestDto customerAddRequestDto = new CustomerAddRequestDto();
        customerAddRequestDto.setPassword("password123");

        Customer customer = new Customer();
        customer.setPassword("encodedPassword");

        when(modelMapper.map(customerAddRequestDto, Customer.class)).thenReturn(customer);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        boolean result = customerService.addCustomer(customerAddRequestDto);

        // Assert
        assertTrue(result);
        verify(customerRepository).save(customer);
    }

    @Test
    public void testFindById_Success() {
        // Arrange
        String customerId = "123";
        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        CustomerFindByIdDto dto = new CustomerFindByIdDto();
        when(modelMapper.map(customer, CustomerFindByIdDto.class)).thenReturn(dto);

        // Act
        CustomerFindByIdDto result = customerService.findById(customerId);

        // Assert
        assertNotNull(result);
        verify(customerRepository).findById(customerId);
    }

    @Test
    public void testFindById_NotFound() {
        // Arrange
        String customerId = "123";
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerService.findById(customerId);
        });

        assertEquals("Error at findByIdService", exception.getMessage());
        verify(customerRepository).findById(customerId);
    }
}
