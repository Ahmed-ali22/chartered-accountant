package com.example.chartered_accountant.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

    @ExtendWith(MockitoExtension.class)
    class JwtTest {

        private Jwt jwt;
        private CustomUserPrincipal principal;
        private String secret;

        @BeforeEach
        void setUp() {
            secret = "mysecretkeymysecretkeymysecretkeymysecretkey";
            jwt = new Jwt(secret, 2000L); 

            principal = new CustomUserPrincipal(
                    UUID.randomUUID(),
                    "ahmed@example.com",
                    "Password123@",
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }

        @Test
        void generateToken_shouldEmbedClaimsCorrectly() {
            String token = jwt.generateToken(principal);
            assertNotNull(token);

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            assertEquals(principal.getUserId().toString(), claims.getSubject());
            assertEquals(principal.getUsername(), claims.get("email", String.class));
            assertEquals("ROLE_USER", claims.get("roles", String.class));
        }

        @Test
        void validateToken_shouldReturnTrueForValidToken() {
            String token = jwt.generateToken(principal);
            assertTrue(jwt.validateToken(token));
        }

        @Test
        void validateToken_shouldReturnFalseForInvalidToken() {
            assertFalse(jwt.validateToken("invalid.token.value"));
        }

        @Test
        void validateToken_shouldReturnFalseForExpiredToken() throws InterruptedException {
            String token = jwt.generateToken(principal);
            Thread.sleep(2500);
            assertFalse(jwt.validateToken(token));
        }

        @Test
        void extractUsername_shouldReturnEmailClaim() {
            String token = jwt.generateToken(principal);
            assertEquals(principal.getUsername(), jwt.extractUsername(token));
        }

        @Test
        void extractUserId_shouldReturnSubjectAsUUID() {
            String token = jwt.generateToken(principal);
            assertEquals(principal.getUserId(), jwt.extractUserId(token));
        }

        @Test
        void extractAuthorities_shouldReturnRoleClaim() {
            String token = jwt.generateToken(principal);
            var authorities = jwt.extractAuthorities(token);
            assertEquals(1, authorities.size());
            assertEquals("ROLE_USER", authorities.iterator().next().getAuthority());
        }
}
