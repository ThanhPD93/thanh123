package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.model.Customer;
import mockProject.team3.Vaccination_20.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController {
    @Autowired
    private CustomerService customerService;


    //use for add injection-rs
    @GetMapping("c-for-add-ir")
    public ResponseEntity<List<Map<String, String>>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();

        List<Map<String, String>> customerInfo = customers.stream().map(customer -> {
            Map<String, String> info = new HashMap<>();
            info.put("id", customer.getCustomerId());
            info.put("name", customer.getFullName());
            info.put("dateOfBirth", customer.getDateOfBirth().toString());
            return info;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(customerInfo);
    }
}
