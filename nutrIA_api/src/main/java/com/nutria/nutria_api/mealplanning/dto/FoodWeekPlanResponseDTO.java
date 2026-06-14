package com.nutria.nutria_api.mealplanning.dto;

import com.nutria.nutria_api.mealplanning.model.TimeDay;
import java.time.LocalDate;

public record FoodWeekPlanResponseDTO(
        Long id,
        Long weekId,
        Long foodId,
        Long userId,
        LocalDate date,
        TimeDay timeDay,
        Double portion
) {}