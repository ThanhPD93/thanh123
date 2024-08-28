package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.model.Admin;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class EmployeeService implements UserDetailsService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String username;
    @Value("${admin.password}")
    private String password;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals(this.username)) {
            return new Admin(this.username, passwordEncoder.encode(this.password));
        }
        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return employee;
    }

    public Employee findById(String id) {
        return employeeRepository.findById(id).get();
    }

    public String getEmailByUsername(String username) {
        return employeeRepository.findByUsername(username).getEmail();
    }

}
