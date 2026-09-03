package com.donat.expensetracker.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JwtServiceTest {

    JwtService jwtService;

    @Test
    void generateTokenThenExtractUsername_returnsSameUsername(){
        jwtService = new JwtService("xKc3fWjC1dwin8Tph2YCp5o0spfdtUFaNfiH1jrBnxk=");
        String token = jwtService.generateToken("test_user", "USER");

        assertEquals("test_user", jwtService.extractUsername(token));
    }

    @Test
    void generateTokenThenExtractRole_returnsSameRole(){
        jwtService = new JwtService("xKc3fWjC1dwin8Tph2YCp5o0spfdtUFaNfiH1jrBnxk=");
        String token = jwtService.generateToken("test_user", "USER");

        assertEquals("USER", jwtService.extractRole(token));
    }
}
