package com.nutria.nutria_api.food.controller;

import com.nutria.nutria_api.food.dto.FoodRequestDTO;
import com.nutria.nutria_api.food.dto.FoodResponseDTO;
import com.nutria.nutria_api.food.service.FoodService;
import com.nutria.nutria_api.shared.pagination.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
@Tag(name = "Foods", description = "Gestión del catálogo de alimentos y su información nutricional")
public class FoodController {

    private final FoodService foodService;

    @Operation(summary = "Listar todos los alimentos paginados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<PageResponse<FoodResponseDTO>> getAll(
            @PageableDefault(size = 10, sort = "foodName") Pageable pageable) {
        return ResponseEntity.ok(foodService.getAllFoods(pageable));
    }

    @Operation(summary = "Obtener alimento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alimento encontrado"),
            @ApiResponse(responseCode = "404", description = "Alimento no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FoodResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    @Operation(summary = "Buscar alimentos por nombre")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente")
    })
    @GetMapping("/search")
    public ResponseEntity<PageResponse<FoodResponseDTO>> searchByName(
            @RequestParam String name,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(foodService.searchByName(name, pageable));
    }

    @Operation(summary = "Filtrar alimentos por tipo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Filtro aplicado correctamente")
    })
    @GetMapping("/type")
    public ResponseEntity<PageResponse<FoodResponseDTO>> getByType(
            @RequestParam String type,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(foodService.getByType(type, pageable));
    }

    @Operation(summary = "Crear un nuevo alimento")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alimento creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FoodResponseDTO> create(@Valid @RequestBody FoodRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(foodService.createFood(request));
    }

    @Operation(summary = "Actualizar un alimento existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alimento actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alimento no encontrado"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FoodResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody FoodRequestDTO request) {
        return ResponseEntity.ok(foodService.updateFood(id, request));
    }

    @Operation(summary = "Eliminar un alimento")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alimento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alimento no encontrado"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }
}