package com.nutria.nutria_api.auth.dto;

public record ChangeEmailRequest(
        String newEmail,
        String password
) {}
