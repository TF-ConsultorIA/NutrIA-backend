package com.nutria.nutria_api.food.repository;

import com.nutria.nutria_api.food.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}