import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;//package mockProject.team3.Vaccination_20.controller;
//
//import mockProject.team3.Vaccination_20.dto.EmployeeCreateRequest;
//import mockProject.team3.Vaccination_20.model.Employee;
//import mockProject.team3.Vaccination_20.service.EmployeeService;
//import mockProject.team3.Vaccination_20.utils.MSGDef;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.method.annotation.PrincipalMethodArgumentResolver;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.security.Principal;
//
//@Controller
//public class LoginController {
//
////    @Value("${admin.username}")
////    private String adminUsername;
//
////    @GetMapping("/login")
////    public String loginPage(@RequestParam(value = "error", required = false) String error,
////                            Model model) {
////        model.addAttribute("employee", new Employee());
////        if (error != null) {
////            model.addAttribute("errorMessage", MSGDef.MSG1.getMessage());
////        }
////        return "MemberLogin";
////    }
//
//@GetMapping("/getEmail")
//public ResponseEntity<String> getEmail(Principal principal) {
//    String email;
//    if (principal.getName().equals("bakaking")) {
//        email = "bakakingAdmin@fpt.com";
//    } else {
//        email = employeeService.getEmailByUsername(principal.getName());
//    }
//    return ResponseEntity.ok(email);
//}
//
//}


