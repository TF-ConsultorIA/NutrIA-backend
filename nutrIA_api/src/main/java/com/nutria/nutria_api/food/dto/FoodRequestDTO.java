package com.nutria.nutria_api.food.dto;

import com.nutria.nutria_api.food.model.FoodType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record FoodRequestDTO(

        @NotBlank(message = "El nombre del alimento es obligatorio")
        String foodName,

        @NotNull(message = "El tipo de alimento es obligatorio")
        FoodType foodType,

        @NotNull(message = "La energía es obligatoria")
        @PositiveOrZero(message = "La energía no puede ser negativa")
        Double energy,

        @NotNull(message = "Las proteínas son obligatorias")
        @PositiveOrZero(message = "Las proteínas no pueden ser negativas")
        Double proteins,

        @NotNull(message = "La grasa total es obligatoria")
        @PositiveOrZero(message = "La grasa total no puede ser negativa")
        Double totalFat,

        @NotNull(message = "El agua es obligatoria")
        @PositiveOrZero(message = "El agua no puede ser negativa")
        Double water,

        @NotNull(message = "Los carbohidratos totales son obligatorios")
        @PositiveOrZero(message = "Los carbohidratos no pueden ser negativos")
        Double carbohydratesTotal,

        @NotNull(message = "El calcio es obligatorio")
        @PositiveOrZero(message = "El calcio no puede ser negativo")
        Double calcium,

        @NotNull(message = "El hierro es obligatorio")
        @PositiveOrZero(message = "El hierro no puede ser negativo")
        Double iron,

        @NotNull(message = "El sodio es obligatorio")
        @PositiveOrZero(message = "El sodio no puede ser negativo")
        Double sodium
) {}