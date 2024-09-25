package mockProject.team3.Vaccination_20.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Operation(summary = "Get the current username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Current username retrieved successfully")
    })
    @GetMapping("/getCurrentUsername")
    public ResponseEntity<String> getCurrentUsername(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }
}
