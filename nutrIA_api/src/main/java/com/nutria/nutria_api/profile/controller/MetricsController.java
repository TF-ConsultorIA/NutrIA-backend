package com.nutria.nutria_api.profile.controller;

import com.nutria.nutria_api.profile.dto.UserMetricCreateRequestDto;
import com.nutria.nutria_api.profile.dto.UserMetricResponseDto;
import com.nutria.nutria_api.profile.service.IUserMetricService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/profiles/{profileId}/metrics")
@RequiredArgsConstructor
@Tag(name = "Metrics", description = "Gestion de metricas corporales")
public class MetricsController {

    private final IUserMetricService userMetricService;

    @Operation(summary = "Listar metricas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de metricas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserMetricResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserMetricResponseDto>> getMetrics(@PathVariable Long profileId) {
        return ResponseEntity.ok(userMetricService.getMetrics(profileId));
    }

    @Operation(summary = "Crear nueva metrica")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Metrica creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserMetricResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<UserMetricResponseDto> createMetric(
            @PathVariable Long profileId,
            @RequestBody UserMetricCreateRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMetricService.createMetric(profileId, request));
    }
}