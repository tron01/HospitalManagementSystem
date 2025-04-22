package com.Abhijith.HospitalManagementSystem.Controller;

import com.Abhijith.HospitalManagementSystem.DTO.AuthRequest;
import com.Abhijith.HospitalManagementSystem.DTO.JwtResponse;
import com.Abhijith.HospitalManagementSystem.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthenticationManager manager;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthController(JwtUtil jwtUtil, AuthenticationManager manager) {
        this.jwtUtil = jwtUtil;
        this.manager = manager;
    }

    @PostMapping("/authenticate")
    private ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        String token= jwtUtil.generateJwtToken(authRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
