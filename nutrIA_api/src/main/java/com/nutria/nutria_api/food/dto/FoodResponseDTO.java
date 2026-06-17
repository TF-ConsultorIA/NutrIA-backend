package com.nutria.nutria_api.food.dto;

import com.nutria.nutria_api.food.model.FoodType;

public record FoodResponseDTO(
        Long id,
        String foodName,
        FoodType foodType,
        String foodGroup,
        Double energy,
        Double proteins,
        Double totalFat,
        Double water,
        Double carbohydratesTotal,
        Double calcium,
        Double iron,
        Double sodium
) {}