package com.Abhijith.HospitalManagementSystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    private boolean isEnabled=true;  // Field to track if the user is enabled
    private boolean isAccountNonLocked=true;  // Field to track account lock status

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;    }

    @Override
    public boolean isEnabled() {
        return isEnabled;    }
}