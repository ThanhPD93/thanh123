package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.employeeDto.*;
import mockProject.team3.Vaccination_20.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    Page<FindAllResponseEmployee> findBySearch(String searchInput, int page, int size);
    List<Employee> findAll();

    List<LResponseEmployee> getAll();

    int addEmployee(CRequestEmployee cRequestEmployee);

    DResponseEmployee updateEmployee(URequestEmployee uRequestEmployee);

    Page<Employee> findAllWithPagination(int page, int size);

    DResponseEmployee findById(String id);

    Employee findEmployeeById(String id);

    void deleteEmployees(List<String> employeeIds);
}