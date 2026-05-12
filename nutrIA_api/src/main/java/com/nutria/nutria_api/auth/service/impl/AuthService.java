package com.nutria.nutria_api.auth.service.impl;

import com.nutria.nutria_api.auth.dto.AuthResponse;
import com.nutria.nutria_api.auth.dto.LoginRequest;
import com.nutria.nutria_api.auth.model.RefreshToken;
import com.nutria.nutria_api.auth.model.User;
import com.nutria.nutria_api.auth.repository.UserRepository;
import com.nutria.nutria_api.auth.service.IAuthService;
import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;

    @Value("${nutria.security.jwt.expiration-ms}")
    private long expirationMs;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.email());
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow();
        String role = user.getRole().name();

        String accessToken = jwtService.GenerateToken(userDetails, role);
        RefreshToken refresh = refreshTokenService.create(user);

        return new AuthResponse(accessToken, refresh.getToken(), user.getEmail(), role, expirationMs);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        RefreshToken curr = refreshTokenService.validate(refreshToken);
        RefreshToken rotated = refreshTokenService.rotate(curr);

        User user = rotated.getUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String role = user.getRole().name();

        String newAccessToken = jwtService.GenerateToken(userDetails, role);
        return new AuthResponse(newAccessToken, rotated.getToken(), user.getEmail(), role, expirationMs);
    }

    @Override
    public void logout(String refreshToken) {
        refreshTokenService.revoke(refreshToken);
    }

    @Override
    public void logoutAll(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Usuario no encontrado")
        );
        refreshTokenService.revokeAllByUserId(user.getId());
    }
}
