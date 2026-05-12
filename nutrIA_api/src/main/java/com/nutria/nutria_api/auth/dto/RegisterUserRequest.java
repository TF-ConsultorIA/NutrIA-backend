package com.nutria.nutria_api.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterUserRequest(
        @NotBlank(message = "El email es obligatorio para el registro")
        @Email(message = "Formato de email invalido")
        String email,

        @NotBlank(message = "El nombre es un campo obligatorio")
        String name,

        String lastNames,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 5, message = "La contraseña debe contener como minimo 5 caracteres")
        String password,

        @NotBlank(message = "La fecha de nacimiento es obligatoria")
        String birthDate,

        @NotBlank(message = "El genero es obligatorio")
        @Pattern(regexp = "(M|F|NE)", message = "El genero debe ser M, F o NE")
        String gender
        ) {}
