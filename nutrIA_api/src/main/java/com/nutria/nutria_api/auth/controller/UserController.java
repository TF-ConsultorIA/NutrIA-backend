package com.nutria.nutria_api.auth.controller;

import com.nutria.nutria_api.auth.dto.UpdateUserRequest;
import com.nutria.nutria_api.auth.dto.UserResponse;
import com.nutria.nutria_api.auth.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag( name = "Users", description = "Endpoints para gestionar al usuario")
public class UserController
{
    private final IUserService userService;

    @Operation(summary = "Obtener tu perfil")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> findMe(Authentication authentication) {
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @Operation(summary = "Actualizar mi perfil")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Perfil actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos invalidos"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMe(
            Authentication authentication,
            @Valid @RequestBody UpdateUserRequest request) {
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        return ResponseEntity.ok(userService.updateByEmail(email, request));
    }

    @Operation(summary = "Eliminar un usuario")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Operacion exitosa"),
            @ApiResponse(responseCode = "403", description = "Sin Permiso")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
