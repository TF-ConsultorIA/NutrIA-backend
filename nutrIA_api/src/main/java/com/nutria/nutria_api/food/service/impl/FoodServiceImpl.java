package com.nutria.nutria_api.food.service.impl;

import com.nutria.nutria_api.food.dto.FoodRequestDTO;
import com.nutria.nutria_api.food.dto.FoodResponseDTO;
import com.nutria.nutria_api.food.exception.FoodNotFoundException;
import com.nutria.nutria_api.food.mapper.FoodMapper;
import com.nutria.nutria_api.food.model.Food;
import com.nutria.nutria_api.food.model.FoodType;
import com.nutria.nutria_api.food.repository.FoodRepository;
import com.nutria.nutria_api.food.service.FoodService;
import com.nutria.nutria_api.shared.pagination.PageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    // US-11 : Listar todos los alimentos
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public PageResponse<FoodResponseDTO> getAllFoods(Pageable pageable) {
        return PageResponse.from(
                foodRepository.findAll(pageable).map(foodMapper::toDTO)
        );
    }

    // US-13 : Detalle de alimento
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public FoodResponseDTO getFoodById(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));
        return foodMapper.toDTO(food);
    }

    // US-11 : Búsqueda de alimentos por nombre
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public PageResponse<FoodResponseDTO> searchByName(String name, Pageable pageable) {
        return PageResponse.from(
                foodRepository.findByFoodNameContainingIgnoreCase(name, pageable)
                        .map(foodMapper::toDTO)
        );
    }

    // US-12 : Filtro de alimentos por tipo
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public PageResponse<FoodResponseDTO> getByType(String type, Pageable pageable) {
        FoodType foodType = FoodType.valueOf(type.toUpperCase());
        return PageResponse.from(
                foodRepository.findByFoodType(foodType, pageable).map(foodMapper::toDTO)
        );
    }

    // US-13 : Crear alimento (ADMIN)
    @Override
    @Transactional
    public FoodResponseDTO createFood(FoodRequestDTO request) {
        Food food = foodMapper.toEntity(request);
        return foodMapper.toDTO(foodRepository.save(food));
    }

    // US-13 : Actualizar alimento (ADMIN)
    @Override
    @Transactional
    public FoodResponseDTO updateFood(Long id, FoodRequestDTO request) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));
        food.setFoodName(request.foodName());
        food.setFoodType(request.foodType());
        food.setEnergy(request.energy());
        food.setProteins(request.proteins());
        food.setTotalFat(request.totalFat());
        food.setWater(request.water());
        food.setCarbohydratesTotal(request.carbohydratesTotal());
        food.setCalcium(request.calcium());
        food.setIron(request.iron());
        food.setSodium(request.sodium());
        return foodMapper.toDTO(foodRepository.save(food));
    }

    // US-13 : Eliminar alimento (ADMIN)
    @Override
    @Transactional
    public void deleteFood(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));
        foodRepository.delete(food);
    }
}