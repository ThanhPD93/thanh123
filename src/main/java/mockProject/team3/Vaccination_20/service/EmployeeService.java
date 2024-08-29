package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployee;
import mockProject.team3.Vaccination_20.dto.response.forlist.LResponseEmployeetest;
import mockProject.team3.Vaccination_20.model.Employee;
import java.util.List;

public interface EmployeeService {
    List<Employee> findBySearch(String searchInput);

    List<LResponseEmployee> getAll();


}