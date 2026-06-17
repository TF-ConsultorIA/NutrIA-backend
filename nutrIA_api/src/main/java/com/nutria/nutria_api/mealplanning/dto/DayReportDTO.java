package com.nutria.nutria_api.mealplanning.dto;

import java.time.LocalDate;
import java.util.List;

public record DayReportDTO(
        LocalDate date,
        List<FoodWeekPlanDetailResponseDTO> desayuno,
        List<FoodWeekPlanDetailResponseDTO> almuerzo,
        List<FoodWeekPlanDetailResponseDTO> cena
) {}