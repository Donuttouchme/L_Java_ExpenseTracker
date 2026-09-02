package com.donat.expensetracker.service;

import com.donat.expensetracker.model.User;
import com.donat.expensetracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsername_userFound(){
        when(userRepository.findByUsername("test_username")).thenReturn(Optional.of(new User("test_username", "test_password", "USER")));

        UserDetails result = customUserDetailsService.loadUserByUsername("test_username");

        assertEquals("test_username", result.getUsername());
        assertEquals("test_password", result.getPassword());
        assertTrue(result.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));

    }

    @Test
    void loadUserByUsername_userNotFound() {
        when(userRepository.findByUsername("test_username")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("test_username"));
    }
}
