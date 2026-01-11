package com.login.authenication.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.authenication.Entity.User;
import com.login.authenication.Security.JwtUtil;
import com.login.authenication.UserRepository.UserRepository;

import jakarta.validation.Valid;
import com.login.authenication.dto.RegisterRequest;
import com.login.authenication.dto.LoginRequest;



@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    
    public AuthController(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil){
   this.jwtUtil=jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTER API
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {

        User user = new User();
      if(userRepository.existsByEmail(request.getEmail())){
    return ResponseEntity.ok("Already Register try Login");
}
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 🔐 HASH
        user.setRole("USER");

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {

    User user = userRepository
            .findByEmail(request.getEmail())
            .orElse(null);

    if (user == null) {
        return ResponseEntity
                .badRequest()
                .body("Email or password not matched");
    }

    if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPassword()
    )) {
        return ResponseEntity
                .badRequest()
                .body("Password not matched");
    }

    String token = jwtUtil.generateToken(
            user.getEmail(),
            user.getRole()
    );

    return ResponseEntity.ok(
            java.util.Map.of("token", token)
    );
}

    
}