package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.employeeDto.*;
import mockProject.team3.Vaccination_20.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    Page<EmployeeResponseDto2> findBySearch(String searchInput, int page, int size);
    List<Employee> findAll();

    List<EmployeeResponseDto3> getAll();

    int addEmployee(EmployeeRequestDto1 employeeRequestDto1);

    EmployeeResponseDto1 updateEmployee(EmployeeRequestDto2 employeeRequestDto2);

    Page<Employee> findAllWithPagination(int page, int size);

    EmployeeResponseDto1 findById(String id);

    Employee findEmployeeById(String id);

    void deleteEmployees(List<String> employeeIds);
}