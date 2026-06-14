package com.nutria.nutria_api.profile.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record UserWeightCreateRequestDto(
        @NotNull(message = "La fecha de registro es obligatoria")
        LocalDate registerDate,

        @NotNull(message = "El peso es obligatorio")
        @Positive(message = "El peso debe ser mayor a 0")
        Double weightKg
) {}
