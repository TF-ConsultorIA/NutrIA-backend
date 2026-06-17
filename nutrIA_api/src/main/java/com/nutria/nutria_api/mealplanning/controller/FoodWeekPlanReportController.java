package com.nutria.nutria_api.mealplanning.controller;

import com.nutria.nutria_api.mealplanning.dto.WeekReportResponseDTO;
import com.nutria.nutria_api.mealplanning.service.FoodWeekPlanReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/meal-plans/report")
@RequiredArgsConstructor
@Tag(name = "Meal Plan Report", description = "Reporte del plan alimenticio semanal actual del estudiante")
public class FoodWeekPlanReportController {

    private final FoodWeekPlanReportService reportService;

    @Operation(summary = "Reporte de la semana actual del usuario organizado por día y momento del día")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte generado correctamente"),
            @ApiResponse(responseCode = "404", description = "No hay semana registrada para hoy")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<WeekReportResponseDTO> getCurrentWeekReport(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.getCurrentWeekReport(userId));
    }
}