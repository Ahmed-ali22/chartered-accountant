package com.example.chartered_accountant.service;

import com.example.chartered_accountant.model.entity.Admin;
import com.example.chartered_accountant.model.entity.User;
import com.example.chartered_accountant.repository.AdminRepo;
import com.example.chartered_accountant.repository.UserRepo;
import com.example.chartered_accountant.util.security.CustomUserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo users;
    private final AdminRepo admins;

    public CustomUserDetailsService(UserRepo users, AdminRepo admins) {
        this.users = users;
        this.admins = admins;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First check in user table
        User u = users.findByEmail(username).orElse(null);
        if (u != null) {
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
            return CustomUserPrincipal.builder()
                    .userId(u.getId())
                    .username(u.getEmail())
                    .password(u.getPassword())
                    .authorities(authorities)
                    .build();
        }
        // If not found, check in admin table
        Admin a = admins.findByUsername(username).orElse(null);
        if (a != null) {
            var authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return CustomUserPrincipal.builder()
                    .userId(a.getId())
                    .username(a.getUsername())
                    .password(a.getPassword())
                    .authorities(authorities)
                    .build();
        }
        // If neither found
        throw new UsernameNotFoundException("User not found: " + username);
    }
    public UserDetails loadUserById(UUID userId) throws UsernameNotFoundException {
        User u = users.findById(userId).orElse(null);
        if (u != null) {
            return CustomUserPrincipal.builder()
                    .userId(u.getId())
                    .username(u.getEmail())
                    .password(u.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                    .build();
        }
        Admin a = admins.findById(userId).orElse(null);
        if (a != null) {
            return CustomUserPrincipal.builder()
                    .userId(a.getId())
                    .username(a.getUsername())
                    .password(a.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with ID: " + userId);
    }
}
