package mockProject.team3.Vaccination_20.service;

import mockProject.team3.Vaccination_20.dto.userDto.UserResponseDto1;

public interface UserService {
    int getRole(String username);
    UserResponseDto1 getUsernameAndEmail(String username);
}
