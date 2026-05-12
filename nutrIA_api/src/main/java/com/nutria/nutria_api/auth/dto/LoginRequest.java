package com.nutria.nutria_api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "El campo email es obligatorio") @Email
        String email,
        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {}
