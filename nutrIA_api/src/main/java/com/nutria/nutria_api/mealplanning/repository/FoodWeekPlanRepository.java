package com.nutria.nutria_api.mealplanning.repository;

import com.nutria.nutria_api.mealplanning.model.FoodWeekPlan;
import com.nutria.nutria_api.mealplanning.model.TimeDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodWeekPlanRepository extends JpaRepository<FoodWeekPlan, Long> {
    List<FoodWeekPlan> findByUserId(Long userId);
    List<FoodWeekPlan> findByUserIdAndWeekId(Long userId, Long weekId);
    List<FoodWeekPlan> findByUserIdAndWeekIdAndTimeDay(Long userId, Long weekId, TimeDay timeDay);
}