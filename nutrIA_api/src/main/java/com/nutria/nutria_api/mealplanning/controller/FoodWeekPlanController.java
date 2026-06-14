package com.nutria.nutria_api.mealplanning.controller;

import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanBulkRequestDTO;
import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanRequestDTO;
import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanResponseDTO;
import com.nutria.nutria_api.mealplanning.service.FoodWeekPlanService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/v1/meal-plans")
@RequiredArgsConstructor
@Tag(name = "Meal Plans", description = "Gestión del plan alimenticio semanal del estudiante")
public class FoodWeekPlanController {

    private final FoodWeekPlanService foodWeekPlanService;

    @Operation(summary = "Obtener todos los planes de un usuario")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Planes obtenidos correctamente")})
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FoodWeekPlanResponseDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(foodWeekPlanService.getPlansByUser(userId));
    }

    @Operation(summary = "Obtener planes de un usuario en una semana específica")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Planes obtenidos correctamente")})
    @GetMapping("/user/{userId}/week/{weekId}")
    public ResponseEntity<List<FoodWeekPlanResponseDTO>> getByUserAndWeek(
            @PathVariable Long userId,
            @PathVariable Long weekId) {
        return ResponseEntity.ok(foodWeekPlanService.getPlansByUserAndWeek(userId, weekId));
    }

    @Operation(summary = "Añadir una comida al plan (manual por timeDay)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comida añadida correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<FoodWeekPlanResponseDTO> addMeal(
            @Valid @RequestBody FoodWeekPlanRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(foodWeekPlanService.addMeal(request));
    }

    @Operation(summary = "Añadir un plan semanal completo (desde chatbot de IA)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Plan semanal añadido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/bulk")
    public ResponseEntity<List<FoodWeekPlanResponseDTO>> addBulkMeals(
            @Valid @RequestBody FoodWeekPlanBulkRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(foodWeekPlanService.addBulkMeals(request));
    }

    @Operation(summary = "Eliminar una comida del plan")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Comida eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        foodWeekPlanService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}
