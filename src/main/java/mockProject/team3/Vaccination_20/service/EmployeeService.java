package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.request.forcreate.CRequestEmployee;
import mockProject.team3.Vaccination_20.dto.request.forupdate.URequestEmployee;
import mockProject.team3.Vaccination_20.dto.response.fordetail.DResponseEmployee;
import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployee;
import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployeetest;
import mockProject.team3.Vaccination_20.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Page<Employee> findBySearchWithPagination(String searchInput, int page, int size);
    List<Employee> findAll();

    List<LResponseEmployee> getAll();

    DResponseEmployee addEmployee(CRequestEmployee cRequestEmployee);

    DResponseEmployee updateEmployee(URequestEmployee uRequestEmployee);

    Page<Employee> findAllWithPagination(int page, int size);

    Employee findById(String id);

    Employee findEmployeeById(String id);

    void deleteEmployees(List<String> employeeIds);
}