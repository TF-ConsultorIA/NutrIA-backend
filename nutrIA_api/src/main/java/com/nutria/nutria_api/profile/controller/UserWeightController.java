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
import jakarta.validation.Valid;
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

    @Operation(summary = "Listar todos los pesos")
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

    @Operation(summary = "Obtener el peso del dia actual",
            description = "Devuelve el registro de peso del dia. Si no existe retorna 204 (sin contenido), lo que indica que aun no se ha registrado el peso del dia.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Peso del dia encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserWeightResponseDto.class))),
            @ApiResponse(responseCode = "204", description = "Sin registro de peso para hoy", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/today")
    public ResponseEntity<UserWeightResponseDto> getWeightToday(@PathVariable Long profileId) {
        return userWeightService.getWeightToday(profileId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @Operation(summary = "Registrar peso del dia")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Peso registrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserWeightResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos o peso ya registrado para esa fecha",
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
            @Valid @RequestBody UserWeightCreateRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userWeightService.createWeight(profileId, request));
    }

    @Operation(summary = "Editar un registro de peso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Peso actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserWeightResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Registro de peso no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{weightId}")
    public ResponseEntity<UserWeightResponseDto> updateWeight(
            @PathVariable Long profileId,
            @PathVariable Long weightId,
            @Valid @RequestBody UserWeightCreateRequestDto request) {

        return ResponseEntity.ok(userWeightService.updateWeight(profileId, weightId, request));
    }
}
