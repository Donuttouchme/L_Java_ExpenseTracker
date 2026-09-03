package com.donat.expensetracker.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}")String _key){

        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(_key));
    }

    public String generateToken(String username, List<String> roles){
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        return parseClaims(token)
                .getSubject();
    }
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token){
        return parseClaims(token)
                .get("roles", List.class);
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
