package com.nutria.nutria_api.mealplanning.service;

import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanBulkRequestDTO;
import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanRequestDTO;
import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanResponseDTO;
import java.util.List;

public interface FoodWeekPlanService {
    List<FoodWeekPlanResponseDTO> getPlansByUser(Long userId);
    List<FoodWeekPlanResponseDTO> getPlansByUserAndWeek(Long userId, Long weekId);
    FoodWeekPlanResponseDTO addMeal(FoodWeekPlanRequestDTO request);
    List<FoodWeekPlanResponseDTO> addBulkMeals(FoodWeekPlanBulkRequestDTO request);
    void deleteMeal(Long id);
}
