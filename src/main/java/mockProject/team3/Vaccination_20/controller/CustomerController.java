package mockProject.team3.Vaccination_20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto4;
import mockProject.team3.Vaccination_20.model.Customer;
import jakarta.validation.Valid;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerRequestDto1;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto1;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerResponseDto2;
import mockProject.team3.Vaccination_20.repository.CustomerRepository;
import mockProject.team3.Vaccination_20.service.CustomerService;
import mockProject.team3.Vaccination_20.utils.MSG;
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

    @Operation(summary = "Using ajax to load content dynamically")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ajax html code loaded successfully"),
            @ApiResponse(responseCode = "400", description = "ajax file name must not be empty!"),
            @ApiResponse(responseCode = "404", description = "ajax path could not find file!")
    })
    @GetMapping("/getAjax")
    public ResponseEntity<String> getDocument(@RequestParam String filename) throws IOException {
        try{
            if (filename == null || filename.isEmpty()) {
                return ResponseEntity.badRequest().body(MSG.MSG31.getMessage());
            }
            ClassPathResource resource = new ClassPathResource("static/html/customer/" + filename);
            Path path = resource.getFile().toPath();
            return ResponseEntity.ok(Files.readString(path));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MSG.MSG32.getMessage());
        }
    }

    @Operation(summary = "find all customers or search customers and put in a pagination list for display")
    @ApiResponse(responseCode = "200", description = "Pagination list of customers found!")
    @GetMapping("/findAllCustomers")
    public ResponseEntity<Page<CustomerResponseDto2>> findAllCustomers(
            @RequestParam String searchInput,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Page<CustomerResponseDto2> customers = customerService.findByFullNameOrAddress(searchInput, page, size);
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "display user name of the current user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "display user name of the current user"),
            @ApiResponse(responseCode = "404", description = "can not found user name!")
    })
    @GetMapping("/getCurrentUsername")
    public ResponseEntity<String> getCurrentUsername(Principal principal) {
        if (principal != null) {
            return ResponseEntity.ok(principal.getName());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found or not authenticated");
        }
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

    @Operation(summary = "Add a new customer or update an existing one")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(type = "string", example = "New customer added(updated) sucessfully"))),
            @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(type = "string", example = "Add(update) failed!")))
    })
    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@Valid @RequestBody CustomerRequestDto1 customerRequestDto1) {


        if (customerService.addCustomer(customerRequestDto1)) {

            return ResponseEntity.ok("New customer added/updated successfully");
        } else {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Add/update failed!");
        }

        return ResponseEntity.ok("Fail to add customer!");
    }

    @Operation(summary = "fetch a customer from database by customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found a customer by customer ID provided"),
            @ApiResponse(responseCode = "404", description = "customer not found by customer ID provided")
    })
    @GetMapping("/findById")
    public ResponseEntity<CustomerResponseDto1> findById(@RequestParam String customerId) {
        CustomerResponseDto1 customer = customerService.findById(customerId);
        if (customer == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    //------
    @Operation(summary = "fetch an customer detail by customer ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found an customer by provided ID"),
            @ApiResponse(responseCode = "404", description = "customer not found by provided ID")
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
                    .body( null);
        }
    }
}
