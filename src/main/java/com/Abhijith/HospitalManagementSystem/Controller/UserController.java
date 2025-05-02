package com.Abhijith.HospitalManagementSystem.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class UserController {

    @GetMapping("/users")
    private List<String> home(){
        return List.of("user","admin","user2","admin2","user3","admin3","user4","admin4");
    }
}

