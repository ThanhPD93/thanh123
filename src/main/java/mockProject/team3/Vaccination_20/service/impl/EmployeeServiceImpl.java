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

import jakarta.persistence.EntityNotFoundException;
import mockProject.team3.Vaccination_20.dto.request.forcreate.CRequestEmployee;
import mockProject.team3.Vaccination_20.dto.request.forupdate.URequestEmployee;
import mockProject.team3.Vaccination_20.dto.response.fordetail.DResponseEmployee;
import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployee;
import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployeetest;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.repository.EmployeeRepository;
import mockProject.team3.Vaccination_20.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<Employee> findBySearchWithPagination(String searchInput, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (searchInput.trim().equals("")) {
            return employeeRepository.findAll(pageable);
        } else {
            return employeeRepository.findBysearch(searchInput, pageable);
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

    @Override
    public DResponseEmployee addEmployee(CRequestEmployee cRequestEmployee) {
        Employee employee = modelMapper.map(cRequestEmployee, Employee.class);
        return modelMapper.map(employeeRepository.save(employee), DResponseEmployee.class);
    }

    @Override
    public DResponseEmployee updateEmployee(URequestEmployee uRequestEmployee) {
        Employee employee = modelMapper.map(uRequestEmployee, Employee.class);
        return modelMapper.map(employeeRepository.save(employee), DResponseEmployee.class);
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
}

