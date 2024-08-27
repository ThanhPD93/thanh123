package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("employee", new Employee());
        return "MemberLogin";
    }
}
