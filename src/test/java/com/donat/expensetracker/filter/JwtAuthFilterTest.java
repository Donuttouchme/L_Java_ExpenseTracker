package com.donat.expensetracker.filter;

import com.donat.expensetracker.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.time.Duration;
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

    @Value("${JWT_SECRET}")
    String secret;

    @Test
    void authenticationWithExpiredToken_returns401() throws Exception{
        JwtService twoHoursAgo = new JwtService(secret, Duration.ofHours(1), Clock.offset(Clock.systemUTC(), Duration.ofHours(-2)));
        String expired = twoHoursAgo.generateToken("test_user", List.of("ROLE_USER"));

        mockMvc.perform(get("/api/expenses")
                .header("Authorization", "Bearer "+ expired))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void tokenWithoutRolesClaim_isStillAuthenticated() throws Exception{
        Instant now = Clock.systemUTC().instant();
        String noRoles = Jwts.builder()
                .subject("test_user")
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(3600)))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)))
                .compact();

        mockMvc.perform(get("/api/expenses")
                        .header("Authorization", "Bearer " + noRoles))
                .andExpect(status().isOk());
    }
}
