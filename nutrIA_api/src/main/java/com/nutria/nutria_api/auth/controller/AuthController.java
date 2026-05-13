package com.nutria.nutria_api.auth.controller;

import com.nutria.nutria_api.auth.dto.*;
import com.nutria.nutria_api.auth.service.IAuthService;
import com.nutria.nutria_api.auth.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Registro, login, refresh, logout, logout all")
public class AuthController {

    private final IUserService userService;
    private final IAuthService authService;

    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o email ya registrado")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterUserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userRequest));
    }

    @Operation(summary = "Login: genera y emite un access token JWT y refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tokens emitidos"),
            @ApiResponse(responseCode = "401", description = "Credenciales invalidas")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Renovar tokens (rotation): emite nuevo access y nuevo refresh")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tokens rotados"),
            @ApiResponse(responseCode = "400", description = "Refresh token invalido, expirado o revocado")
    })
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request.refreshToken()));
    }

    @Operation(summary = "Cerrar sesion en el dispositivo actual: revoca el refresh token")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Sesion cerrada")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshRequest request) {
        authService.logout(request.refreshToken());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cerrar sesion del usuario en todos los dispositivos autenticados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Todas las sesiones cerradas"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @PostMapping("/logout-all")
    public ResponseEntity<Void> logoutAll(Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        authService.logoutAll(principal.getUsername());
        return ResponseEntity.noContent().build();
    }
}
