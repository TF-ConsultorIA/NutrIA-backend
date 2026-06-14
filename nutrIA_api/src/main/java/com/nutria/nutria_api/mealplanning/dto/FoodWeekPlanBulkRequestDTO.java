package com.nutria.nutria_api.mealplanning.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

// Usado por el chatbot de IA para enviar un plan semanal completo de golpe
public record FoodWeekPlanBulkRequestDTO(

        @NotNull(message = "El id de semana es obligatorio")
        Long weekId,

        @NotNull(message = "El id de usuario es obligatorio")
        Long userId,

        @NotEmpty(message = "El plan no puede estar vacío")
        List<FoodWeekPlanRequestDTO> plans
) {}