package com.nutria.nutria_api.mealplanning.dto;

import com.nutria.nutria_api.food.dto.FoodResponseDTO;
import com.nutria.nutria_api.mealplanning.model.TimeDay;
import java.time.LocalDate;

public record FoodWeekPlanDetailResponseDTO(
        Long id,
        Long weekId,
        Long userId,
        LocalDate date,
        TimeDay timeDay,
        Double portion,
        FoodResponseDTO food
) {}