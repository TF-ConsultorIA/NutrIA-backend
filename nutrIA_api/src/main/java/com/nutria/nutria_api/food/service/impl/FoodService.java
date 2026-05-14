package com.nutria.nutria_api.food.service;

import com.nutria.nutria_api.food.dto.FoodRequestDTO;
import com.nutria.nutria_api.food.dto.FoodResponseDTO;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FoodService {
    Page<FoodResponseDTO> getAllFoods(Pageable pageable);
    FoodResponseDTO getFoodById(Long id);
    Page<FoodResponseDTO> searchByName(String name, Pageable pageable);
    Page<FoodResponseDTO> getByType(String type, Pageable pageable);
    FoodResponseDTO createFood(FoodRequestDTO request);
    FoodResponseDTO updateFood(Long id, FoodRequestDTO request);
    void deleteFood(Long id);
}