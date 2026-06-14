package com.nutria.nutria_api.mealplanning.mapper;

import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanRequestDTO;
import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanResponseDTO;
import com.nutria.nutria_api.mealplanning.model.FoodWeekPlan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodWeekPlanMapper {
    FoodWeekPlan toEntity(FoodWeekPlanRequestDTO dto);
    FoodWeekPlanResponseDTO toDTO(FoodWeekPlan plan);
}