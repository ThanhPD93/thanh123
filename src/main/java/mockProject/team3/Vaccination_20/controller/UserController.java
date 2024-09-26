package mockProject.team3.Vaccination_20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import mockProject.team3.Vaccination_20.dto.userDto.UserResponseDto1;
import mockProject.team3.Vaccination_20.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Operation(summary = "Get the current username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Current username retrieved successfully")
    })
    @GetMapping("/getCurrentUsernameAndEmail")
    public ResponseEntity<UserResponseDto1> getCurrentUsernameAndEmail(Principal principal) {
        UserResponseDto1 user = userService.getUsernameAndEmail(principal.getName());
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getRole")
    public ResponseEntity<String> getRole(Principal principal) {
		int result = userService.getRole(principal.getName());
        if (result == 0) {
            return ResponseEntity.badRequest().body("no roles found!!");
        } else if (result == 1) {
            return ResponseEntity.ok("admin");
        }
        return ResponseEntity.ok("employee");
    }
}
