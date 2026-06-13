package com.nutria.nutria_api.streak.controller;

import com.nutria.nutria_api.streak.dto.StreakRequestDTO;
import com.nutria.nutria_api.streak.dto.StreakResponseDTO;
import com.nutria.nutria_api.streak.service.StreakService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/streaks")
@RequiredArgsConstructor
@Tag(name = "Streaks", description = "Gestión de rachas de uso de la aplicación")
public class StreakController {

    private final StreakService streakService;

    @Operation(summary = "Obtener racha por ID de usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Racha encontrada"),
            @ApiResponse(responseCode = "404", description = "Racha no encontrada")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<StreakResponseDTO> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(streakService.getStreakByUserId(userId));
    }

    @Operation(summary = "Registrar una nueva racha")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Racha creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StreakResponseDTO> create(@Valid @RequestBody StreakRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(streakService.createStreak(request));
    }

    @Operation(summary = "Check-in diario del usuario — actualiza la racha automáticamente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Check-in registrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Racha no encontrada")
    })
    @PostMapping("/user/{userId}/check-in")
    public ResponseEntity<StreakResponseDTO> checkIn(@PathVariable Long userId) {
        return ResponseEntity.ok(streakService.checkIn(userId));
    }

    @Operation(summary = "Actualizar racha manualmente (solo ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Racha actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Racha no encontrada"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PutMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StreakResponseDTO> update(@PathVariable Long userId,
                                                    @Valid @RequestBody StreakRequestDTO request) {
        return ResponseEntity.ok(streakService.updateStreak(userId, request));
    }

    @Operation(summary = "Eliminar racha de un usuario")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Racha eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Racha no encontrada"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @DeleteMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        streakService.deleteStreak(userId);
        return ResponseEntity.noContent().build();
    }
}