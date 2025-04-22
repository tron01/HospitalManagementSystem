package com.Abhijith.HospitalManagementSystem.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final long EXPIRATION_TIME = 1000*60; //1 hr
    private final String SECRET = "@2313_here_my_secret_key_@#@!!323525262 ";
    private final SecretKey Key = Keys.hmacShaKeyFor(SECRET.getBytes());
    //Generate Jwt Token
    public String generateJwtToken(String userDetails) {
        return Jwts.builder()
                .setSubject(userDetails)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(Key, SignatureAlgorithm.HS256)
                .compact();
    }
    //Extract claims
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    //Validate Token
    public boolean validateToken(String username, UserDetails userDetails, String token) {
        // check if username from token is same as username in userDetails
        // check if token is not expired
         return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //Extract Username from token
    public  String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    //Extract isTokenExpired
    private Boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

}
