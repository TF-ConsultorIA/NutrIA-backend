package com.nutria.nutria_api.mealplanning.controller;

import com.nutria.nutria_api.mealplanning.dto.WeekRequestDTO;
import com.nutria.nutria_api.mealplanning.dto.WeekResponseDTO;
import com.nutria.nutria_api.mealplanning.service.WeekService;
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
@RequestMapping("/api/v1/weeks")
@RequiredArgsConstructor
@Tag(name = "Weeks", description = "Gestión del calendario de semanas del año académico")
public class WeekController {

    private final WeekService weekService;

    @Operation(summary = "Listar todas las semanas paginadas")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")})
    @GetMapping
    public ResponseEntity<PageResponse<WeekResponseDTO>> getAll(
            @PageableDefault(size = 20, sort = "startDate") Pageable pageable) {
        return ResponseEntity.ok(weekService.getAllWeeks(pageable));
    }

    @Operation(summary = "Obtener semana por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Semana encontrada"),
            @ApiResponse(responseCode = "404", description = "Semana no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<WeekResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(weekService.getWeekById(id));
    }

    @Operation(summary = "Obtener la semana actual según la fecha de hoy")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Semana actual encontrada"),
            @ApiResponse(responseCode = "404", description = "No hay semana registrada para hoy")
    })
    @GetMapping("/current")
    public ResponseEntity<WeekResponseDTO> getCurrent() {
        return ResponseEntity.ok(weekService.getCurrentWeek());
    }

    @Operation(summary = "Crear una nueva semana (solo ADMIN)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Semana creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WeekResponseDTO> create(@Valid @RequestBody WeekRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(weekService.createWeek(request));
    }
}