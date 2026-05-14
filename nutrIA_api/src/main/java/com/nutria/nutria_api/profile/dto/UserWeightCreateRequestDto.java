package com.nutria.nutria_api.profile.dto;

import java.time.LocalDate;

public record UserWeightCreateRequestDto(
        LocalDate registerDate,
        Double weightKg
) {}