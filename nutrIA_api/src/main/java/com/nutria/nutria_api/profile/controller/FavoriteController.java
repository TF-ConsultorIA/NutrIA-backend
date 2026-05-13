package com.nutria.nutria_api.profile.controller;

import com.nutria.nutria_api.profile.dto.FavoriteCreateRequestDto;
import com.nutria.nutria_api.profile.dto.FavoriteResponseDto;
import com.nutria.nutria_api.profile.service.IFavoriteService;
import com.nutria.nutria_api.shared.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles/{profileId}/favorites")
@RequiredArgsConstructor
@Tag(name = "Favorites", description = "Gestion de favoritos")
public class FavoriteController {

    private final IFavoriteService favoriteService;

    @Operation(summary = "Listar favoritos paginados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de favoritos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoriteResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<Page<FavoriteResponseDto>> getFavorites(
            @PathVariable Long profileId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                favoriteService.getFavorites(profileId, PageRequest.of(page, size))
        );
    }

    @Operation(summary = "Agregar favorito")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Favorito creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoriteResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos invalidos",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Perfil o alimento no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Favorito duplicado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<FavoriteResponseDto> addFavorite(
            @PathVariable Long profileId,
            @RequestBody FavoriteCreateRequestDto request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(favoriteService.addFavorite(profileId, request));
    }

    @Operation(summary = "Eliminar favorito")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Favorito eliminado", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Favorito no encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(
            @PathVariable Long profileId,
            @PathVariable Long favoriteId) {

        favoriteService.deleteFavorite(profileId, favoriteId);
        return ResponseEntity.noContent().build();
    }
}