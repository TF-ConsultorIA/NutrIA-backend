package com.nutria.nutria_api.streak.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public record StreakRequestDTO(

        @NotNull(message = "El id de usuario es obligatorio")
        @Positive(message = "El id de usuario debe ser mayor a 0")
        Long userId,

        @NotNull(message = "El número de racha es obligatorio")
        @PositiveOrZero(message = "El número de racha no puede ser negativo")
        Integer streakNumber,

        @NotNull(message = "La fecha del último login es obligatoria")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate lastLogin
) {}