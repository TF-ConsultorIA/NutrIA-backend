package com.nutria.nutria_api.food.exception;

public class FoodNotFoundException extends RuntimeException {
    public FoodNotFoundException(Long id) {
        super("Food not found with id: " + id);
    }
}