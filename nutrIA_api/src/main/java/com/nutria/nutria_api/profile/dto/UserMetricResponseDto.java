package com.nutria.nutria_api.profile.dto;

public record UserMetricResponseDto(
        Long id,
        Double height,
        Double chest,
        Double arm
) {}