package mockProject.team3.Vaccination_20.service.impl;

//import mockProject.team3.Vaccination_20.model.Employee;
//import mockProject.team3.Vaccination_20.repository.EmployeeRepository;
//import mockProject.team3.Vaccination_20.service.EmployeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmployeeServiceImpl implements EmployeeService {
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//
//    @Value("${admin.username}")
//    private String username;
//    @Value("${admin.password}")
//    private String password;
//
//
//    public Employee findById(String id) {
//        return employeeRepository.findById(id).get();
//    }
//
//    public String getEmailByUsername(String username) {
//        return employeeRepository.findByUsername(username).getEmail();
//    }
//
//}

import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.repository.EmployeeRepository;
import mockProject.team3.Vaccination_20.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findBySearch(String searchInput) {
        return employeeRepository.findBysearch(searchInput);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
