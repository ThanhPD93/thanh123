package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.request.forcreate.CRequestEmployee;
import mockProject.team3.Vaccination_20.dto.request.forupdate.URequestEmployee;
import mockProject.team3.Vaccination_20.dto.response.fordetail.DResponseEmployee;
import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployee;
import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployeetest;
import mockProject.team3.Vaccination_20.model.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> findBySearch(String searchInput);
    List<Employee> findAll();

    List<LResponseEmployee> getAll();

    DResponseEmployee addEmployee(CRequestEmployee cRequestEmployee);

    DResponseEmployee updateEmployee(URequestEmployee uRequestEmployee);
}