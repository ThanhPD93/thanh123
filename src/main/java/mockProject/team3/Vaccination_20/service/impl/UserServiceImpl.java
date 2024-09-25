//package mockProject.team3.Vaccination_20.service.impl;
//
//import mockProject.team3.Vaccination_20.dto.UserDto.UserResponseDto1;
//import mockProject.team3.Vaccination_20.repository.EmployeeRepository;
//import mockProject.team3.Vaccination_20.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserServiceImpl implements UserService {
//	@Autowired
//    AdminRepository adminRepository;
//    @Autowired
//    EmployeeRepository employeeRepository;
//    UserResponseDto1 getCurrentUsernameAndRole(String username) {
//        UserResponseDto1 user = new UserResponseDto1();
//        if(adminRepository.findByUsername(username).isPresent()) {
//            user.setUsername(username);
//            user.setRole("Admin");
//            return user;
//        } else if(employeeRepository.findByUsername(username).isPresent()) {
//			user.setUsername(username);
//            user.setRole("Employee");
//            return user;
//        }
//        else return null;
//    }
//}
