package com.nutria.nutria_api.profile.controller;

import com.nutria.nutria_api.profile.dto.*;
import com.nutria.nutria_api.profile.service.IPreferenceService;
import com.nutria.nutria_api.shared.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles/{profileId}/preferences")
@RequiredArgsConstructor
@Tag(name = "Preferences", description = "Gestion de preferencias del perfil")
public class PreferenceController {

    private final IPreferenceService preferenceService;

    @Operation(summary = "Listar preferencias")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preferencias del perfil",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PreferenceResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<PreferenceResponseDto> getPreferences(@PathVariable Long profileId) {
        return ResponseEntity.ok(preferenceService.getPreferences(profileId));
    }

    @Operation(summary = "Actualizar preferencias en bloque")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Preferencias actualizadas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PreferenceResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping
    public ResponseEntity<PreferenceResponseDto> updatePreferences(
            @PathVariable Long profileId,
            @RequestBody PreferenceUpdateRequestDto request) {
        return ResponseEntity.ok(preferenceService.updatePreferences(profileId, request));
    }

    @Operation(summary = "Agregar preferencia individual")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Preferencia creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PreferenceItemDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil o alimento no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Preferencia duplicada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<PreferenceItemDto> addPreference(
            @PathVariable Long profileId,
            @RequestBody PreferenceCreateRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(preferenceService.addPreference(profileId, request));
    }

    @Operation(summary = "Eliminar preferencia individual")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Preferencia eliminada", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Preferencia no encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{preferenceId}")
    public ResponseEntity<Void> deletePreference(
            @PathVariable Long profileId,
            @PathVariable Long preferenceId) {
        preferenceService.deletePreference(profileId, preferenceId);
        return ResponseEntity.noContent().build();
    }
}