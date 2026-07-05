package com.nutria.nutria_api.chatbot.tool;

import com.nutria.nutria_api.food.dto.FoodResponseDTO;
import com.nutria.nutria_api.food.model.FoodType;
import com.nutria.nutria_api.food.service.impl.FoodService;
import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanDetailResponseDTO;
import com.nutria.nutria_api.mealplanning.service.FoodWeekPlanService;
import com.nutria.nutria_api.profile.dto.PreferenceItemDto;
import com.nutria.nutria_api.profile.dto.PreferenceResponseDto;
import com.nutria.nutria_api.profile.service.IPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FoodTool {
    private final FoodService foodService;
    private final IPreferenceService preferenceService;
    private final FoodWeekPlanService planService;

    @Tool(description = "Get all the authenticated user's food preferences: allergies, liked and disliked foods. " +
            "Use it whenever the user asks for meal recommendations or meal planning to personalize suggestions.")
    public PreferenceResponseDto getUserPreferences() {
        return new PreferenceResponseDto(
                preferenceService.getMyTypePreference("allergy"),
                preferenceService.getMyTypePreference("liked"),
                preferenceService.getMyTypePreference("disliked")
        );
    }

    @Tool(description = "Get the authenticated user's current weekly meal plan. " +
            "Use it to check what meals are already planned before suggesting changes or new meals.")
    public List<FoodWeekPlanDetailResponseDTO> getWeeklyPlan() {
        return planService.getMyCurrentWeekPlan();
    }

    @Tool(description = "Search meals in the database by their name (partial match). " +
            "Use this ONLY when the user mentions a specific dish name or part of it, e.g. 'lomo saltado', 'ceviche'. " +
            "Do NOT use this for nutrient-based searches like 'high protein' or 'low calorie' meals.")
    public List<FoodResponseDTO> searchMeals(
            @ToolParam(description = "Partial or full name of the dish, e.g. 'lomo'") String query
    ) {
        Pageable pageable = PageRequest.of(0, 10);
        return foodService.searchByName(query, FoodType.PLATE, pageable).content();
    }

    @Tool(description = "Get a specific food/meal by its ID, including its full nutritional info " +
            "(per 100g). Use this when you already have a food ID (e.g. from allergies, likes, " +
            "dislikes, or weekly plan) and need its name or nutritional details.")
    public FoodResponseDTO getFoodById(
            @ToolParam(description = "The ID of the food/meal to retrieve") Long foodId
    ) {
        return foodService.getFoodById(foodId);
    }
}
