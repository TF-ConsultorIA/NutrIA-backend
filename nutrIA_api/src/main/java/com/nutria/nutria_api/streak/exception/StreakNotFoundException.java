package com.nutria.nutria_api.streak.exception;

public class StreakNotFoundException extends RuntimeException {
    public StreakNotFoundException(Long userId) {
        super("Streak not found for user with id: " + userId);
    }
}