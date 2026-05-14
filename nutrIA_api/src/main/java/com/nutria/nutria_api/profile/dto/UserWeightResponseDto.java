package com.nutria.nutria_api.profile.dto;

import java.time.LocalDate;

public record UserWeightResponseDto(
        Long id,
        LocalDate registerDate,
        Double weightKg
) {}