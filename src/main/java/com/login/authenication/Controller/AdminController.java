package com.login.authenication.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.authenication.Entity.User;
import com.login.authenication.UserRepository.UserRepository;
import com.login.authenication.dto.UserResponse;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')") 
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        List<User> users=userRepository.findAll();
        List<UserResponse> userdto=new ArrayList<>();
        for(User user:users){
            UserResponse userResponse=new UserResponse(
           user.getId(),user.getEmail(),user.getRole()
            );
            userdto.add(userResponse);
        }
return userdto;
    }
}
