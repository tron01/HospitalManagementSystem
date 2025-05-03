package com.Abhijith.HospitalManagementSystem.Config;

import com.Abhijith.HospitalManagementSystem.ExceptionHandler.CustomAuthenticationEntryPoint;
import com.Abhijith.HospitalManagementSystem.ExceptionHandler.CustomaccessDeniedHandler;
import com.Abhijith.HospitalManagementSystem.Filter.JwtFilter;
import com.Abhijith.HospitalManagementSystem.Service.CustomUserServiceDetails;
import com.Abhijith.HospitalManagementSystem.Util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserServiceDetails customUserServiceDetails;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomaccessDeniedHandler accessDeniedHandler;

    @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                    .exceptionHandling(e->
                            e.authenticationEntryPoint(authenticationEntryPoint)
                                    .accessDeniedHandler(accessDeniedHandler))
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(
                        req -> req.requestMatchers("/api/auth/login",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html").permitAll()
                                .requestMatchers("/api/doctor/register").permitAll()
                                .requestMatchers("/api/patient/register","swagger-ui/index.html").permitAll()
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/patient/**").hasAnyRole("PATIENT","ADMIN")
                                .requestMatchers("/api/doctor/**").hasAnyRole("DOCTOR","ADMIN")
                                .anyRequest().authenticated())
                                .sessionManagement(s->
                                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtFilter
                                ,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserServiceDetails);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

}
