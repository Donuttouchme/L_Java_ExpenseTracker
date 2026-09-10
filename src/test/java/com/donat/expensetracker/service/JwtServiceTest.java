package com.donat.expensetracker.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtServiceTest {

    private static final String SECRET = "xKc3fWjC1dwin8Tph2YCp5o0spfdtUFaNfiH1jrBnxk=";

    JwtService jwtService = new JwtService(SECRET, Duration.ofHours(1), Clock.systemUTC());

    @Test
    void generateTokenThenExtractUsername_returnsSameUsername(){
        String token = jwtService.generateToken("test_user", List.of("USER"));

        assertEquals("test_user", jwtService.extractUsername(token));
    }

    @Test
    void generateTokenThenExtractRole_returnsSameRole(){
        String token = jwtService.generateToken("test_user", List.of("ROLE_USER", "ROLE_ADMIN"));

        assertEquals(List.of("ROLE_USER", "ROLE_ADMIN"), jwtService.extractRoles(token));
    }

    @Test
    void extractRolesWithoutRolesClaim_returnsEmptyList(){
        String tokenWithoutRoles = Jwts.builder()
                .subject("test_user")
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET)))
                .compact();

        assertEquals(List.of(), jwtService.extractRoles(tokenWithoutRoles));
    }
}
