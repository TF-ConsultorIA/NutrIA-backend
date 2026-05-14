package com.nutria.nutria_api.profile.dto;

public record PreferenceItemDto(
        Long id,
        Long foodId,
        String type
) {}