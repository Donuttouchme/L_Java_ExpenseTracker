package com.donat.expensetracker.filter;

import com.donat.expensetracker.service.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtAuthFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtService jwtService;

    @Test
    void authenticationWithValidToken_returns200() throws Exception{
        String token = jwtService.generateToken("test_user", List.of("ROLE_USER"));
        mockMvc.perform(get("/api/expenses")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void authenticationWithOutToken_returns401() throws Exception{
        mockMvc.perform(get("/api/expenses"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticationWithInvalidToken_returns401() throws Exception{
        mockMvc.perform(get("/api/expenses")
                        .header("Authorization", "Bearer not-a-real-token"))
                .andExpect(status().isUnauthorized());
    }
}
