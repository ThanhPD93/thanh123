package mockProject.team3.Vaccination_20.service.impl;

import mockProject.team3.Vaccination_20.dto.userDto.UserResponseDto1;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.repository.EmployeeRepository;
import mockProject.team3.Vaccination_20.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public int getRole(String username) {
        if(username.equals(adminUsername)) {
            return 1;
        } else if(employeeRepository.findByUsername(username) != null) {
            return 2;
        }
        else return 0;
    }

    @Override
    public UserResponseDto1 getUsernameAndEmail(String currentUsername) {
        UserResponseDto1 user = new UserResponseDto1();
        if(currentUsername.equals(adminUsername)) {
            user.setUsername(currentUsername);
            user.setEmail("admin@fsoft.com.vn");
			return user;
        } else {
            Employee employee = employeeRepository.findByUsername(currentUsername);
            if (employee != null) {
                user.setUsername(employee.getUsername());
                user.setEmail(employee.getEmail());
                return user;
            }
        }
        return null;
    }
}
