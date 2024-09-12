package mockProject.team3.Vaccination_20.controller;

import jakarta.validation.Valid;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerAddRequestDto;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerFindByIdDto;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerFindByIdRequestDto;
import mockProject.team3.Vaccination_20.dto.customerDto.CustomerListResponseDto;
import mockProject.team3.Vaccination_20.service.CustomerService;
import mockProject.team3.Vaccination_20.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/getAjax")
    public String getDocument(@RequestParam String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/html/customer/" + filename);
        Path path = resource.getFile().toPath();
        return Files.readString(path);
    }

    @GetMapping("/findAllCustomers")
    public ResponseEntity<ApiResponse<Page<CustomerListResponseDto>>> findAllCustomers(
            @RequestParam String searchInput,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        //method body
        Page<CustomerListResponseDto> customers = customerService.findByFullNameOrAddress(searchInput, page, size);
        if (customers.isEmpty()) {
            ApiResponse<Page<CustomerListResponseDto>> apiResponse = new ApiResponse<>(
                    0,
                    "No data found!",
                    customers);
            return ResponseEntity.ok(apiResponse);
        } else {
            System.out.println("checkpoint 2");
            ApiResponse<Page<CustomerListResponseDto>> apiResponse = new ApiResponse<>(
                    1,
                    "Data found!",
                    customers);
            return ResponseEntity.ok(apiResponse);
        }
    }

    @GetMapping("/getCurrentUsername")
    public ResponseEntity<String> getCurrentUsername(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteCustomers(@RequestBody List<String> customerIds) {
        if (customerIds == null || customerIds.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "No data deleted!", null));
        }

        try {
            customerService.deleteCustomers(customerIds);
            return ResponseEntity.ok(new ApiResponse<>(200, "Customers deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "An error occurred: " + e.getMessage(), null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@Valid @RequestBody CustomerAddRequestDto customerAddRequestDto) {
        System.out.println("entering /customer/add endpoint!");
        if (customerService.addCustomer(customerAddRequestDto)) {
            System.out.println("enterting if->true!");
            return ResponseEntity.ok("Add customer success!");
        }
        System.out.println("entering if->false!");
        return ResponseEntity.ok("Fail to add customer!");
    }

    @GetMapping("/findById")
    public ResponseEntity<CustomerFindByIdDto> findById(@RequestParam String id) {
        return ResponseEntity.ok().body(customerService.findById(id));
    }
}

