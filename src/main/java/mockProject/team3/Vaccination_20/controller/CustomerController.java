package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto4;
import mockProject.team3.Vaccination_20.model.Customer;
import jakarta.validation.Valid;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerRequestDto1;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto1;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto2;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Operation(summary = "Load an HTML file dynamically via AJAX")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HTML file loaded successfully"),
            @ApiResponse(responseCode = "404", description = "File not found")
    })
    @GetMapping("/getAjax")
    public ResponseEntity<String> getDocument(@RequestParam String filename) throws IOException {
        try {
            ClassPathResource resource = new ClassPathResource("static/html/customer/" + filename);
            Path path = resource.getFile().toPath();
            return ResponseEntity.ok(Files.readString(path));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }
    }

    @Operation(summary = "Find all customers with optional search and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagination list of customers found")
    })
    @GetMapping("/findAllCustomers")
    public ResponseEntity<Page<CustomerResponseDto2>> findAllCustomers(
            @RequestParam String searchInput,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<CustomerResponseDto2> customers = customerService.findByFullNameOrAddress(searchInput, page, size);
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Get the current username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Current username retrieved successfully")
    })
    @GetMapping("/getCurrentUsername")
    public ResponseEntity<String> getCurrentUsername(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }

    @Operation(summary = "Delete one or more customers by customer IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers deleted successfully"),
            @ApiResponse(responseCode = "400", description = "No data deleted"),
            @ApiResponse(responseCode = "500", description = "An error occurred")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCustomers(@RequestBody List<String> customerIds) {
        if (customerIds == null || customerIds.isEmpty()) {
            return ResponseEntity.badRequest().body("No data deleted!");
        }

        try {
            customerService.deleteCustomers(customerIds);
            return ResponseEntity.ok("Customers deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }

    @Operation(summary = "Add a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer added successfully"),
            @ApiResponse(responseCode = "400", description = "Failed to add customer")
    })
    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@Valid @RequestBody CustomerRequestDto1 customerRequestDto1) {
        if (customerService.addCustomer(customerRequestDto1)) {
            return ResponseEntity.ok("Add customer success!");
        }
        return ResponseEntity.ok("Fail to add customer!");
    }

    @Operation(summary = "Find a customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/findById")
    public ResponseEntity<CustomerResponseDto1> findById(@RequestParam String customerId) {
        CustomerResponseDto1 customer = customerService.findById(customerId);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get customer details by customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer details found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/detail/{customerInfoId}")
    public ResponseEntity<CustomerResponseDto4> getCustomerDetail(@PathVariable String customerInfoId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerInfoId);

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            CustomerResponseDto4 customerDTO = new CustomerResponseDto4(customer.getCustomerId(), customer.getFullName(), customer.getDateOfBirth());
            return ResponseEntity.ok(customerDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
