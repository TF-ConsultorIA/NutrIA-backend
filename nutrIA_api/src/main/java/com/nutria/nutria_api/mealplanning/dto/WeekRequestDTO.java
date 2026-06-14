package com.nutria.nutria_api.mealplanning.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record WeekRequestDTO(

        @NotNull(message = "El número de semana es obligatorio")
        @Positive(message = "El número de semana debe ser mayor a 0")
        Integer week,

        @NotNull(message = "El año es obligatorio")
        @Positive(message = "El año debe ser mayor a 0")
        Integer year,

        @NotNull(message = "La fecha de inicio es obligatoria")
        LocalDate startDate,

        @NotNull(message = "La fecha de fin es obligatoria")
        LocalDate endDate
) {}