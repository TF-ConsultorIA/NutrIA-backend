package com.nutria.nutria_api.mealplanning.exception;

import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;

public class WeekNotFoundException extends ResourceNotFoundException {
    public WeekNotFoundException(Long id) {
        super("La semana con id " + id + " no fue encontrada");
    }
}
