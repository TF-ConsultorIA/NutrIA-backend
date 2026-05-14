package com.nutria.nutria_api.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(
        @NotBlank
        String oldPassword,

        @NotBlank
        String newPassword
) {}
