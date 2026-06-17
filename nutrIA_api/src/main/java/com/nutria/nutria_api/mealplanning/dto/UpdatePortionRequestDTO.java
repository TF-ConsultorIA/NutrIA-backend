package com.nutria.nutria_api.mealplanning.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdatePortionRequestDTO(
        @NotNull(message = "La porción es obligatoria")
        @Positive(message = "La porción debe ser mayor a 0")
        Double portion
) {}