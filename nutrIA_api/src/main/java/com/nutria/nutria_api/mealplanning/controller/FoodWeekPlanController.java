package com.nutria.nutria_api.mealplanning.controller;

import com.nutria.nutria_api.mealplanning.dto.*;
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FoodWeekPlanResponseDTO>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(foodWeekPlanService.getPlansByUser(userId));
    }

    @Operation(summary = "Planes de un usuario en una semana, con info nutricional completa")
    @GetMapping("/user/{userId}/week/{weekId}")
    public ResponseEntity<List<FoodWeekPlanDetailResponseDTO>> getByUserAndWeek(
            @PathVariable Long userId,
            @PathVariable Long weekId) {
        return ResponseEntity.ok(foodWeekPlanService.getPlansByUserAndWeek(userId, weekId));
    }

    @PostMapping
    public ResponseEntity<FoodWeekPlanResponseDTO> addMeal(
            @Valid @RequestBody FoodWeekPlanRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(foodWeekPlanService.addMeal(request));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<FoodWeekPlanResponseDTO>> addBulkMeals(
            @Valid @RequestBody FoodWeekPlanBulkRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(foodWeekPlanService.addBulkMeals(request));
    }

    @Operation(summary = "Editar la porción de una comida ya planificada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Porción actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FoodWeekPlanResponseDTO> updatePortion(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePortionRequestDTO request) {
        return ResponseEntity.ok(foodWeekPlanService.updatePortion(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long id) {
        foodWeekPlanService.deleteMeal(id);
        return ResponseEntity.noContent().build();
    }
}