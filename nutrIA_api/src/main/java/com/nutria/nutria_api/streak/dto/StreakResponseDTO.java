package com.nutria.nutria_api.streak.dto;

import java.time.LocalDate;

public record StreakResponseDTO(
        Long id,
        Long userId,
        Integer streakNumber,
        LocalDate lastLogin
) {}
