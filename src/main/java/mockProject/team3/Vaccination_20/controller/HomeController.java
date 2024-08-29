package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.EmployeeCreateRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String home() {
        return "/home/dashboard";
    }
}
