package mockProject.team3.Vaccination_20.service.impl;

import mockProject.team3.Vaccination_20.model.Admin;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.repository.EmployeeRepository;
import mockProject.team3.Vaccination_20.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import mockProject.team3.Vaccination_20.dto.request.forcreate.CRequestEmployee;
import mockProject.team3.Vaccination_20.dto.request.forupdate.URequestEmployee;
import mockProject.team3.Vaccination_20.dto.response.fordetail.DResponseEmployee;
import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployee;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Base64;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService, UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public Page<Employee> findBySearchWithPagination(String searchInput, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (searchInput.trim().equals("")) {
            return employeeRepository.findAll(pageable);
        } else {
            return employeeRepository.findBySearch(searchInput, pageable);
        }
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<LResponseEmployee> getAll() {
        return employeeRepository.findAllBy();
    }

//    @Override
//    public DResponseEmployee addEmployee(CRequestEmployee cRequestEmployee) {
//        Employee employee = modelMapper.map(cRequestEmployee, Employee.class);
//        return modelMapper.map(employeeRepository.save(employee), DResponseEmployee.class);
//    }

    @Override
    public DResponseEmployee addEmployee(CRequestEmployee cRequestEmployee) {
        Employee employee;
        if(cRequestEmployee.getImage() == null) {
           byte[] currentImage = employeeRepository.findByEmployeeId(cRequestEmployee.getEmployeeId()).getImage();
            employee = modelMapper.map(cRequestEmployee, Employee.class);
            employee.setImage(currentImage);
        } else {
            employee = modelMapper.map(cRequestEmployee, Employee.class);
        }

        if (cRequestEmployee.getImage() != null && !cRequestEmployee.getImage().isEmpty()) {
            try {
                // Clean and decode the Base64 string
                String base64Image = cRequestEmployee.getImage();
                byte[] decodedImage = Base64.getDecoder().decode(base64Image);
                employee.setImage(decodedImage);
            } catch (IllegalArgumentException e) {
                System.err.println("Failed to decode image: " + e.getMessage());
                throw new RuntimeException("Invalid Base64 image data", e);
            }
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return modelMapper.map(employeeRepository.save(employee), DResponseEmployee.class);
    }

    @Override
    public DResponseEmployee updateEmployee(URequestEmployee uRequestEmployee) {
        // Fetch the existing employee by ID
        Employee existingEmployee = employeeRepository.findById(uRequestEmployee.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Map the DTO to the existing entity, keeping existing data if necessary
        modelMapper.map(uRequestEmployee, existingEmployee);

        // If a new image is provided, decode and set it
        if (uRequestEmployee.getImage() != null && !uRequestEmployee.getImage().isEmpty()) {
            try {
                String base64Image = uRequestEmployee.getImage();
                byte[] decodedImage = Base64.getDecoder().decode(base64Image);
                existingEmployee.setImage(decodedImage);
            } catch (IllegalArgumentException e) {
                System.err.println("Failed to decode image: " + e.getMessage());
                throw new RuntimeException("Invalid Base64 image data", e);
            }
        }

        // Save the updated employee
        return modelMapper.map(employeeRepository.save(existingEmployee), DResponseEmployee.class);
    }

    @Override
    public Page<Employee> findAllWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Employee findById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
    }

    @Override
    public Employee findEmployeeById(String id) {
        return employeeRepository.findByEmployeeId(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(adminUsername)) {
            String encodedAdminPassword = passwordEncoder.encode(adminPassword);
            return new Admin(adminUsername, encodedAdminPassword);
        }
        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return employee;
    }

}

