package com.nutria.nutria_api.auth.service.impl;

import com.nutria.nutria_api.auth.dto.*;
import com.nutria.nutria_api.auth.exception.EmailAlreadyExistsException;
import com.nutria.nutria_api.auth.exception.NotStudentEmailException;
import com.nutria.nutria_api.auth.model.RefreshToken;
import com.nutria.nutria_api.auth.model.Role;
import com.nutria.nutria_api.auth.model.User;
import com.nutria.nutria_api.auth.repository.UserRepository;
import com.nutria.nutria_api.auth.service.IAuthService;
import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Value("${nutria.security.jwt.expiration-ms}")
    private long expirationMs;

    @Override
    // US-02 : Inicio de sesión
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
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

    @Override
    @Transactional
    // US-04 : Actualización de seguridad de la cuenta
    public ChangeCredentialsResponse changeEmail(String email, ChangeEmailRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.password())
        );

        // RN-01 : Unicidad de cuenta por correo
        if (userRepository.existsByEmail(request.newEmail())) {
            throw new EmailAlreadyExistsException(request.newEmail());
        }

        User user = userRepository.findByEmail(email).orElseThrow();

        // RN-26 : Registro de correo estudiantil
        if (user.getRole() == Role.YOUNGER &&
                !request.newEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.edu(\\.[a-zA-Z]{2})?$")) {
            throw new NotStudentEmailException();
        }

        user.setEmail(request.newEmail());
        return getChangeCredentialsResponse(user);
    }

    @Override
    @Transactional
    // US-04 : Actualización de seguridad de la cuenta
    public ChangeCredentialsResponse changePassword(String email, ChangePasswordRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.oldPassword())
        );

        User user = userRepository.findByEmail(email).orElseThrow();
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        return getChangeCredentialsResponse(user);
    }


    // Funcion auxiliar para actualizar credenciales
    @NonNull
    private ChangeCredentialsResponse getChangeCredentialsResponse(User user) {
        userRepository.save(user);
        refreshTokenService.revokeAllByUserId(user.getId());

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())
                .build();

        UsernamePasswordAuthenticationToken newAuth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(newAuth);
        String role = user.getRole().name();
        String accessToken = jwtService.GenerateToken(userDetails, role);
        RefreshToken refresh = refreshTokenService.create(user);

        return new ChangeCredentialsResponse(accessToken, refresh.getToken(), user.getEmail(), role, expirationMs);
    }
}
