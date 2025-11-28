package com.example.chartered_accountant.util.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil {
    private final Key key;
    private final Long expirationMillis;

    public JwtUtil(String secret, Long expirationMillis) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMillis = expirationMillis;
    }
    public String generateToken(CustomUserPrincipal userDetails) {
        String roles = userDetails.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
        Date now = new Date();
        Date exp = new Date(now.getTime() +  expirationMillis);

        return Jwts.builder()
                .setSubject(userDetails.getUserId().toString())
                .claim("roles",roles)
                .claim("email",userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
    public String extractUsername(String token) {
        return parseClaims(token).get("email", String.class);
    }
    public UUID extractUserId(String token) {
        return UUID.fromString(parseClaims(token).getSubject());
    }
    public Collection <SimpleGrantedAuthority> extractAuthorities(String token) {
        String roles = parseClaims(token).get("roles",String.class);
        if(roles == null || roles.isBlank()) return List.of();
        return Arrays.stream(roles.split(","))
                .filter(r -> !r.isBlank())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }
}
