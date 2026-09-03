package com.donat.expensetracker.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}")String _key){

        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(_key));
    }

    public String generateToken(String username, String role){
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        return parseClaims(token)
                .getSubject();
    }

    public String extractRole(String token){
        return parseClaims(token)
                .get("role", String.class);
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
