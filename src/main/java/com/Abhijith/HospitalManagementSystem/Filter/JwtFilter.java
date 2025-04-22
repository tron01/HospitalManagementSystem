package com.Abhijith.HospitalManagementSystem.Filter;

import com.Abhijith.HospitalManagementSystem.Service.CustomUserServiceDetails;
import com.Abhijith.HospitalManagementSystem.Util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

     private final JwtUtil jwtUtil;
     private final CustomUserServiceDetails CustomUserServiceDetails;

     @Autowired
     public JwtFilter(JwtUtil jwtUtil, CustomUserServiceDetails CustomUserServiceDetails) {
        this.jwtUtil = jwtUtil;
        this.CustomUserServiceDetails = CustomUserServiceDetails;
     }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String authToken = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authToken = authHeader.substring(7);
            username  = jwtUtil.extractUsername(authToken);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // fetch user by username
            UserDetails userDetails = CustomUserServiceDetails.loadUserByUsername(username);
            // validate Token
            if(jwtUtil.validateToken(username, userDetails, authToken)) {
                UsernamePasswordAuthenticationToken authenticationTokenToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationTokenToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationTokenToken);

            }
        }

        filterChain.doFilter(request, response);
    }
}
