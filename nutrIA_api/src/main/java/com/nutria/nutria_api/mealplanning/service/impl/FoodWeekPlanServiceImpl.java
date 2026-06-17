package com.nutria.nutria_api.mealplanning.service.impl;

import com.nutria.nutria_api.food.mapper.FoodMapper;
import com.nutria.nutria_api.food.model.Food;
import com.nutria.nutria_api.food.repository.FoodRepository;
import com.nutria.nutria_api.mealplanning.dto.*;
import com.nutria.nutria_api.mealplanning.exception.FoodWeekPlanNotFoundException;
import com.nutria.nutria_api.mealplanning.mapper.FoodWeekPlanMapper;
import com.nutria.nutria_api.mealplanning.model.FoodWeekPlan;
import com.nutria.nutria_api.mealplanning.repository.FoodWeekPlanRepository;
import com.nutria.nutria_api.mealplanning.service.FoodWeekPlanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodWeekPlanServiceImpl implements FoodWeekPlanService {

    private final FoodWeekPlanRepository foodWeekPlanRepository;
    private final FoodWeekPlanMapper foodWeekPlanMapper;
    private final FoodRepository foodRepository;
    private final FoodMapper foodMapper;

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<FoodWeekPlanResponseDTO> getPlansByUser(Long userId) {
        return foodWeekPlanRepository.findByUserId(userId)
                .stream()
                .map(foodWeekPlanMapper::toDTO)
                .toList();
    }

    // Trae los planes de la semana con la info nutricional completa del alimento
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<FoodWeekPlanDetailResponseDTO> getPlansByUserAndWeek(Long userId, Long weekId) {
        List<FoodWeekPlan> plans = foodWeekPlanRepository.findByUserIdAndWeekId(userId, weekId);

        List<Long> foodIds = plans.stream().map(FoodWeekPlan::getFoodId).distinct().toList();
        Map<Long, Food> foodsById = foodRepository.findAllById(foodIds)
                .stream()
                .collect(Collectors.toMap(Food::getId, f -> f));

        return plans.stream()
                .map(plan -> new FoodWeekPlanDetailResponseDTO(
                        plan.getId(),
                        plan.getWeekId(),
                        plan.getUserId(),
                        plan.getDate(),
                        plan.getTimeDay(),
                        plan.getPortion(),
                        foodMapper.toDTO(foodsById.get(plan.getFoodId()))
                ))
                .toList();
    }

    @Override
    @Transactional
    public FoodWeekPlanResponseDTO addMeal(FoodWeekPlanRequestDTO request) {
        FoodWeekPlan plan = foodWeekPlanMapper.toEntity(request);
        return foodWeekPlanMapper.toDTO(foodWeekPlanRepository.save(plan));
    }

    @Override
    @Transactional
    public List<FoodWeekPlanResponseDTO> addBulkMeals(FoodWeekPlanBulkRequestDTO request) {
        List<FoodWeekPlan> plans = request.plans()
                .stream()
                .map(foodWeekPlanMapper::toEntity)
                .toList();
        return foodWeekPlanRepository.saveAll(plans)
                .stream()
                .map(foodWeekPlanMapper::toDTO)
                .toList();
    }

    // Editar solo la porción de una comida ya planificada
    @Override
    @Transactional
    public FoodWeekPlanResponseDTO updatePortion(Long id, UpdatePortionRequestDTO request) {
        FoodWeekPlan plan = foodWeekPlanRepository.findById(id)
                .orElseThrow(() -> new FoodWeekPlanNotFoundException(id));
        plan.setPortion(request.portion());
        return foodWeekPlanMapper.toDTO(foodWeekPlanRepository.save(plan));
    }

    @Override
    @Transactional
    public void deleteMeal(Long id) {
        FoodWeekPlan plan = foodWeekPlanRepository.findById(id)
                .orElseThrow(() -> new FoodWeekPlanNotFoundException(id));
        foodWeekPlanRepository.delete(plan);
    }
}