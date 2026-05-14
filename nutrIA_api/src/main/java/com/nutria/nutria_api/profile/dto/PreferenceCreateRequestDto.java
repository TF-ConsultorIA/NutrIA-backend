package com.nutria.nutria_api.profile.dto;

public record PreferenceCreateRequestDto(
        Long foodId,
        String type
) {}