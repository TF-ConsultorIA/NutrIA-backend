package com.nutria.nutria_api.food.repository;

import com.nutria.nutria_api.food.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    Page<Food> findByFoodNameContainingIgnoreCase(String foodName, Pageable pageable);
    Page<Food> findByFoodType(String foodType, Pageable pageable);
}