package com.nutria.nutria_api.mealplanning.exception;

import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;

public class FoodWeekPlanNotFoundException extends ResourceNotFoundException {
    public FoodWeekPlanNotFoundException(Long id) {
        super("El plan de comida con id " + id + " no fue encontrado");
    }
}

