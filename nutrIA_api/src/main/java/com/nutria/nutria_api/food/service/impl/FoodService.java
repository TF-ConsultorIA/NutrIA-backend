package com.nutria.nutria_api.food.service;

import com.nutria.nutria_api.food.dto.FoodRequestDTO;
import com.nutria.nutria_api.food.dto.FoodResponseDTO;

import java.util.List;

public interface FoodService {
    List<FoodResponseDTO> getAllFoods();
    FoodResponseDTO getFoodById(Long id);
    List<FoodResponseDTO> searchByName(String name);
    List<FoodResponseDTO> getByType(String type);
    FoodResponseDTO createFood(FoodRequestDTO request);
    FoodResponseDTO updateFood(Long id, FoodRequestDTO request);
    void deleteFood(Long id);
}