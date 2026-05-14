package com.nutria.nutria_api.food.mapper;

import com.nutria.nutria_api.food.dto.FoodRequestDTO;
import com.nutria.nutria_api.food.dto.FoodResponseDTO;
import com.nutria.nutria_api.food.model.Food;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    public Food toEntity(FoodRequestDTO dto) {
        return Food.builder()
                .foodName(dto.getFoodName())
                .foodType(dto.getFoodType())
                .energy(dto.getEnergy())
                .proteins(dto.getProteins())
                .totalFat(dto.getTotalFat())
                .water(dto.getWater())
                .carbohydratesTotal(dto.getCarbohydratesTotal())
                .calcium(dto.getCalcium())
                .iron(dto.getIron())
                .sodium(dto.getSodium())
                .build();
    }

    public FoodResponseDTO toDTO(Food food) {
        return FoodResponseDTO.builder()
                .id(food.getId())
                .foodName(food.getFoodName())
                .foodType(food.getFoodType())
                .energy(food.getEnergy())
                .proteins(food.getProteins())
                .totalFat(food.getTotalFat())
                .water(food.getWater())
                .carbohydratesTotal(food.getCarbohydratesTotal())
                .calcium(food.getCalcium())
                .iron(food.getIron())
                .sodium(food.getSodium())
                .build();
    }
}