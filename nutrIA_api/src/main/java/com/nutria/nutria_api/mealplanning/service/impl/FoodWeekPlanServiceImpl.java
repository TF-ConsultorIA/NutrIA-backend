package com.nutria.nutria_api.mealplanning.service.impl;

import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanBulkRequestDTO;
import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanRequestDTO;
import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanResponseDTO;
import com.nutria.nutria_api.mealplanning.exception.FoodWeekPlanNotFoundException;
import com.nutria.nutria_api.mealplanning.mapper.FoodWeekPlanMapper;
import com.nutria.nutria_api.mealplanning.model.FoodWeekPlan;
import com.nutria.nutria_api.mealplanning.repository.FoodWeekPlanRepository;
import com.nutria.nutria_api.mealplanning.service.FoodWeekPlanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodWeekPlanServiceImpl implements FoodWeekPlanService {

    private final FoodWeekPlanRepository foodWeekPlanRepository;
    private final FoodWeekPlanMapper foodWeekPlanMapper;

    // Obtener todos los planes de un usuario
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<FoodWeekPlanResponseDTO> getPlansByUser(Long userId) {
        return foodWeekPlanRepository.findByUserId(userId)
                .stream()
                .map(foodWeekPlanMapper::toDTO)
                .toList();
    }

    // Obtener planes de un usuario en una semana específica
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<FoodWeekPlanResponseDTO> getPlansByUserAndWeek(Long userId, Long weekId) {
        return foodWeekPlanRepository.findByUserIdAndWeekId(userId, weekId)
                .stream()
                .map(foodWeekPlanMapper::toDTO)
                .toList();
    }

    // Añadir una comida manualmente (por timeDay desde el calendario)
    @Override
    @Transactional
    public FoodWeekPlanResponseDTO addMeal(FoodWeekPlanRequestDTO request) {
        FoodWeekPlan plan = foodWeekPlanMapper.toEntity(request);
        return foodWeekPlanMapper.toDTO(foodWeekPlanRepository.save(plan));
    }

    // Añadir un plan semanal completo de golpe (desde el chatbot de IA)
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

    // Eliminar una comida del plan
    @Override
    @Transactional
    public void deleteMeal(Long id) {
        FoodWeekPlan plan = foodWeekPlanRepository.findById(id)
                .orElseThrow(() -> new FoodWeekPlanNotFoundException(id));
        foodWeekPlanRepository.delete(plan);
    }
}