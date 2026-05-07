package com.example.chartered_accountant.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Jwt jwt;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtAuthenticationFilter(Jwt jwt, CustomUserDetailsService customUserDetailsService) {
        this.jwt = jwt;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException,
            IOException {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            if(jwt.validateToken(token)) {
                UUID userId = jwt.extractUserId(token);
                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                var authorities = jwt.extractAuthorities(token);
                var auth = new UsernamePasswordAuthenticationToken(userDetails , null , authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request,response);
    }
}
