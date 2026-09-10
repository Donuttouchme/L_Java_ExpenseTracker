package com.donat.expensetracker.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private final SecretKey key;
    private final Duration expiration;
    private final Clock clock;

    public JwtService(@Value("${jwt.secret}")String key,
                      @Value("${jwt.expiration}") Duration expiration,
                      Clock clock){

        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        this.expiration = expiration;
        this.clock = clock;
    }

    public String generateToken(String username, List<String> roles){
        Instant now = clock.instant();
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expiration)))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        return parseClaims(token)
                .getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) throws JwtException {
        List<String> roles = parseClaims(token)
                .get("roles", List.class);
        if (roles == null){
            roles = List.of();
        }
        return roles;
    }

    private Claims parseClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
