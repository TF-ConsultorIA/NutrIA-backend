package com.nutria.nutria_api.profile.controller;

import com.nutria.nutria_api.profile.dto.UserWeightCreateRequestDto;
import com.nutria.nutria_api.profile.dto.UserWeightResponseDto;
import com.nutria.nutria_api.profile.service.IUserWeightService;
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
@RequestMapping("/api/v1/profiles/{profileId}/weights")
@RequiredArgsConstructor
@Tag(name = "Weights", description = "Gestion de pesos del usuario")
public class UserWeightController {

    private final IUserWeightService userWeightService;

    @Operation(summary = "Listar pesos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de pesos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserWeightResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserWeightResponseDto>> getWeights(@PathVariable Long profileId) {
        return ResponseEntity.ok(userWeightService.getWeights(profileId));
    }

    @Operation(summary = "Registrar peso")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Peso registrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserWeightResponseDto.class))),
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
    public ResponseEntity<UserWeightResponseDto> createWeight(
            @PathVariable Long profileId,
            @RequestBody UserWeightCreateRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userWeightService.createWeight(profileId, request));
    }
}