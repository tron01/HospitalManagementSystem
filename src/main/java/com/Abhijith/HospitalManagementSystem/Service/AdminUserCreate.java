package com.Abhijith.HospitalManagementSystem.Service;

import com.Abhijith.HospitalManagementSystem.Model.User;
import com.Abhijith.HospitalManagementSystem.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserCreate {

    @Bean
    public CommandLineRunner UserAdminCreate(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User user = new User();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("admin"));
                user.setRole("ROLE_ADMIN");
                userRepository.save(user);
                System.out.println("✅ Admin user created");
            } else {
                System.out.println("⚠️ Admin user already exists");
            }
        };
    }
}

