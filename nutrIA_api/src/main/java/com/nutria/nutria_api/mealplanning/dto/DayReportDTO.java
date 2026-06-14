package com.nutria.nutria_api.mealplanning.dto;

import java.time.LocalDate;
import java.util.List;

public record DayReportDTO(
        LocalDate date,
        List<FoodWeekPlanResponseDTO> desayuno,
        List<FoodWeekPlanResponseDTO> almuerzo,
        List<FoodWeekPlanResponseDTO> cena
) {}