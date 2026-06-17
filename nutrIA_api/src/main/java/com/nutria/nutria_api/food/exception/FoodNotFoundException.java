package com.nutria.nutria_api.food.exception;

import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;

public class FoodNotFoundException extends ResourceNotFoundException {
    public FoodNotFoundException(Long id) {
        super("El alimento con id " + id + " no fue encontrado");
    }
}