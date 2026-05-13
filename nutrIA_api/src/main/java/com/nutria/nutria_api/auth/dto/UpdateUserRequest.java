package com.nutria.nutria_api.auth.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

public record UpdateUserRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,

        @NotBlank(message = "Los apellidos son obligatorios")
        String lastNames,

        @NotBlank(message = "La fecha de nacimiento es obligatoria")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        String birthDate,

        @NotBlank(message = "El genero es obligatorio")
        @Pattern(regexp = "(H|M|NE)", message = "El genero debe ser H, M o NE")
        String gender
) {}
