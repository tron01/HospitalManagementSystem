package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.AuthRequest;
import com.Abhijith.HospitalManagementSystem.DTO.JwtResponse;
import com.Abhijith.HospitalManagementSystem.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager manager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(JwtUtil jwtUtil, AuthenticationManager manager) {
        this.jwtUtil = jwtUtil;
        this.manager = manager;
    }

    @PostMapping("/login")
    private ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        String token= jwtUtil.generateJwtToken(authRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
