package com.nutria.nutria_api.auth.dto;

public record ChangeCredentialsResponse(
        String accessToken,
        String refreshToken,
        String email,
        String role,
        long accessExpiresInMs) {}
