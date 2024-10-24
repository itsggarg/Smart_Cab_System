package com.smartcab.smart_cab_system.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcab.smart_cab_system.model.User;
import com.smartcab.smart_cab_system.service.CustomUserDetailsService;
import com.smartcab.smart_cab_system.service.UserService;
import com.smartcab.smart_cab_system.config.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userService.checkIfUserExists(user.getEmail())) {
            return ResponseEntity.badRequest().body("User with this email already exists.");
        }
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        Optional<User> user = userService.findByEmail(loginRequest.getEmail());
        if (user.isPresent()) {
            // Use PasswordEncoder to match the password
            if (userService.getPasswordEncoder().matches(loginRequest.getPassword(), user.get().getPassword())) {

                // Authenticate the user
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

                // Load user details to generate JWT token
                UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
                String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

                return ResponseEntity.ok(jwtToken); // Return the JWT token
            } else {
                return ResponseEntity.status(401).body("Invalid email or password.");
            }
        } else {
            return ResponseEntity.status(401).body("Invalid email or password.");
        }
    }
}
