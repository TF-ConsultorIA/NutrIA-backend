package com.nutria.nutria_api.food.mapper;

import com.nutria.nutria_api.food.dto.FoodRequestDTO;
import com.nutria.nutria_api.food.dto.FoodResponseDTO;
import com.nutria.nutria_api.food.model.Food;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodMapper {
    Food toEntity(FoodRequestDTO dto);
    FoodResponseDTO toDTO(Food food);
}