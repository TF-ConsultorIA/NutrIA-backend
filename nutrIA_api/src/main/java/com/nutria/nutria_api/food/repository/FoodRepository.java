package com.nutria.nutria_api.food.repository;

import com.nutria.nutria_api.food.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByFoodNameContainingIgnoreCase(String foodName);
    List<Food> findByFoodType(String foodType);
}