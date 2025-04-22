package com.Abhijith.HospitalManagementSystem.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final long EXPIRATION_TIME = 1000*60*60; //1 hr
    private final String SECRET = "@2313_here_my_secret_key_@#@!!323525262 ";
    private final SecretKey Key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateJwtToken(String userDetails) {
        return Jwts.builder()
                .setSubject(userDetails)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key, SignatureAlgorithm.HS256)
                .compact();
    }

}
