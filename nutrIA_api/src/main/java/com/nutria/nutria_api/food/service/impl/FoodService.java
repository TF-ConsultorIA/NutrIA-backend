package com.nutria.nutria_api.food.service.impl;

import com.nutria.nutria_api.food.dto.FoodRequestDTO;
import com.nutria.nutria_api.food.dto.FoodResponseDTO;
import com.nutria.nutria_api.food.model.FoodType;
import com.nutria.nutria_api.shared.pagination.PageResponse;
import org.springframework.data.domain.Pageable;

public interface FoodService {
    PageResponse<FoodResponseDTO> getAllFoods(Pageable pageable);
    FoodResponseDTO getFoodById(Long id);
    PageResponse<FoodResponseDTO> searchByName(String name, FoodType foodType, Pageable pageable);
    PageResponse<FoodResponseDTO> getByType(String type, Pageable pageable);
    FoodResponseDTO createFood(FoodRequestDTO request);
    FoodResponseDTO updateFood(Long id, FoodRequestDTO request);
    void deleteFood(Long id);
}