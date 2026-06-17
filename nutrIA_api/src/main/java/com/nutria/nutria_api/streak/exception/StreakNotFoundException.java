package com.nutria.nutria_api.streak.exception;

import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;

public class StreakNotFoundException extends ResourceNotFoundException {
    public StreakNotFoundException(Long userId) {
        super("La racha del usuario con id " + userId + " no fue encontrada");
    }
}