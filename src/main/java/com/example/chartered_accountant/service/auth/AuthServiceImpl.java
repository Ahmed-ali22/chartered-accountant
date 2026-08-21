package com.example.chartered_accountant.service.auth;

import com.example.chartered_accountant.error.exception.TokenException;
import com.example.chartered_accountant.model.dto.Auth.AuthRequestDto;
import com.example.chartered_accountant.model.dto.Auth.AuthResponseDto;
import com.example.chartered_accountant.model.dto.Auth.RefreshTokenRequestDto;
import com.example.chartered_accountant.model.entity.RefreshToken;
import com.example.chartered_accountant.security.CustomUserDetailsService;
import com.example.chartered_accountant.security.CustomUserPrincipal;
import com.example.chartered_accountant.security.Jwt;
import com.example.chartered_accountant.service.refreshtoken.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManager authManager;
    private final Jwt jwt;
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authManager, Jwt jwt ,
                           RefreshTokenService refreshTokenService, CustomUserDetailsService userDetailsService) {
        this.authManager = authManager;
        this.jwt = jwt;
        this.refreshTokenService = refreshTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AuthResponseDto login(AuthRequestDto authRequestDto) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestDto.getEmail(),
                            authRequestDto.getPassword()
                    )
            );
            CustomUserPrincipal userDetails = (CustomUserPrincipal) auth.getPrincipal();
            String accessToken = jwt.generateToken(userDetails);
            RefreshToken refreshToken = refreshTokenService
                    .createRefreshToken(userDetails.getUserId());
            return new AuthResponseDto(accessToken, refreshToken.getToken());

        } catch (BadCredentialsException e) {
            throw new TokenException(401, "Invalid Credentials",
                    "Email or password is incorrect.");
        }
    }

    @Override
    public AuthResponseDto refreshSession(RefreshTokenRequestDto requestDto) {
        RefreshToken token = refreshTokenService.findByToken(requestDto.getRefreshToken())
                .orElseThrow(() -> new TokenException(401, "Invalid Session Token", "Refresh token is not present in the system database."));
        RefreshToken verifiedToken = refreshTokenService.verifyExpiration(token);
        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) userDetailsService.loadUserById(verifiedToken.getOwnerId());
        String newAccessToken = jwt.generateToken(userPrincipal);
        return new AuthResponseDto(newAccessToken, verifiedToken.getToken());
    }

    @Override
    public void logout(UUID ownerId) {
        if (ownerId == null) {
            throw new TokenException(401, "Unauthorized Action", "Invalid session details provided.");
        }
        refreshTokenService.deleteByOwnerId(ownerId);
    }

}
