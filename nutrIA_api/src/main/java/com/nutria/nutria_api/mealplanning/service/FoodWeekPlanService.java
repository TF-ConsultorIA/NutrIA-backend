package com.nutria.nutria_api.mealplanning.service;

import com.nutria.nutria_api.mealplanning.dto.*;

import java.util.List;

public interface FoodWeekPlanService {
    List<FoodWeekPlanResponseDTO> getPlansByUser(Long userId);
    List<FoodWeekPlanDetailResponseDTO> getPlansByUserAndWeek(Long userId, Long weekId);
    FoodWeekPlanResponseDTO addMeal(FoodWeekPlanRequestDTO request);
    List<FoodWeekPlanResponseDTO> addBulkMeals(FoodWeekPlanBulkRequestDTO request);
    FoodWeekPlanResponseDTO updatePortion(Long id, UpdatePortionRequestDTO request);
    void deleteMeal(Long id);
}