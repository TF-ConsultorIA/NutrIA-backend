package com.nutria.nutria_api.mealplanning.dto;

import com.nutria.nutria_api.mealplanning.model.TimeDay;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record FoodWeekPlanRequestDTO(

        @NotNull(message = "El id de semana es obligatorio")
        Long weekId,

        @NotNull(message = "El id de alimento es obligatorio")
        Long foodId,

        @NotNull(message = "El id de usuario es obligatorio")
        Long userId,

        @NotNull(message = "La fecha es obligatoria")
        LocalDate date,

        @NotNull(message = "El momento del día es obligatorio")
        TimeDay timeDay,

        @NotNull(message = "La porción es obligatoria")
        @Positive(message = "La porción debe ser mayor a 0")
        Double portion
) {}