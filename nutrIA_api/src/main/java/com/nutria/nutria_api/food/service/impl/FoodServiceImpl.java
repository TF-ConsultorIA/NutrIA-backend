package com.nutria.nutria_api.food.service.impl;

import com.nutria.nutria_api.food.dto.FoodRequestDTO;
import com.nutria.nutria_api.food.dto.FoodResponseDTO;
import com.nutria.nutria_api.food.exception.FoodNotFoundException;
import com.nutria.nutria_api.food.mapper.FoodMapper;
import com.nutria.nutria_api.food.model.Food;
import com.nutria.nutria_api.food.repository.FoodRepository;
import com.nutria.nutria_api.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    @Override
    public List<FoodResponseDTO> getAllFoods() {
        return foodRepository.findAll()
                .stream()
                .map(foodMapper::toDTO)
                .toList();
    }

    @Override
    public FoodResponseDTO getFoodById(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));
        return foodMapper.toDTO(food);
    }

    @Override
    public List<FoodResponseDTO> searchByName(String name) {
        return foodRepository.findByFoodNameContainingIgnoreCase(name)
                .stream()
                .map(foodMapper::toDTO)
                .toList();
    }

    @Override
    public List<FoodResponseDTO> getByType(String type) {
        return foodRepository.findByFoodType(type)
                .stream()
                .map(foodMapper::toDTO)
                .toList();
    }

    @Override
    public FoodResponseDTO createFood(FoodRequestDTO request) {
        Food food = foodMapper.toEntity(request);
        return foodMapper.toDTO(foodRepository.save(food));
    }

    @Override
    public FoodResponseDTO updateFood(Long id, FoodRequestDTO request) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));
        food.setFoodName(request.getFoodName());
        food.setFoodType(request.getFoodType());
        food.setEnergy(request.getEnergy());
        food.setProteins(request.getProteins());
        food.setTotalFat(request.getTotalFat());
        food.setWater(request.getWater());
        food.setCarbohydratesTotal(request.getCarbohydratesTotal());
        food.setCalcium(request.getCalcium());
        food.setIron(request.getIron());
        food.setSodium(request.getSodium());
        return foodMapper.toDTO(foodRepository.save(food));
    }

    @Override
    public void deleteFood(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));
        foodRepository.delete(food);
    }
}