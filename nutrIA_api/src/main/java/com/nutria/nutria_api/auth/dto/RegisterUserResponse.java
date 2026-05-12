package com.nutria.nutria_api.auth.dto;

import java.time.LocalDate;

public record RegisterUserResponse(
        Long userId,
        String email,
        String name,
        String lastNames,
        String gender,
        LocalDate birthDate
) { }
