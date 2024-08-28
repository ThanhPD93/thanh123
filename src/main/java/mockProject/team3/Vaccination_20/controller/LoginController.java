package mockProject.team3.Vaccination_20.controller;

import mockProject.team3.Vaccination_20.dto.EmployeeCreateRequest;
import mockProject.team3.Vaccination_20.model.Employee;
import mockProject.team3.Vaccination_20.service.EmployeeService;
import mockProject.team3.Vaccination_20.utils.MSGDef;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.PrincipalMethodArgumentResolver;

import java.security.Principal;

@Controller
public class LoginController {
	@Autowired
    private EmployeeService employeeService;

    @Value("${admin.username}")
    private String adminUsername;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
            Model model){
        model.addAttribute("employee", new Employee());
        if(error != null) {
            model.addAttribute("errorMessage", MSGDef.MSG1.getMessage());
        }
        return "MemberLogin";
    }

    @GetMapping("/home")
    public String home(Principal principal,
                       Model model) {
        if (principal.getName().equals(adminUsername)) {
            model.addAttribute("email", "admin@fpt.com");
        } else {
            model.addAttribute("email", employeeService.getEmailByUsername(principal.getName()));
        }
        model.addAttribute("employee", new EmployeeCreateRequest());
        return "dashboard";
    }
}
