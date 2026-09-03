package com.donat.expensetracker.controller;

import com.donat.expensetracker.dto.LoginRequest;
import com.donat.expensetracker.dto.LoginResponse;
import com.donat.expensetracker.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager _authenticationManager, JwtService _jwtService){
        authenticationManager = _authenticationManager;
        jwtService = _jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
            List<String> roles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            return new LoginResponse(jwtService.generateToken(auth.getName(), roles));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid login credentials!");
        }
    }

}
