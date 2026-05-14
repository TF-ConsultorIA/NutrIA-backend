package com.nutria.nutria_api.profile.dto;

public record UserMetricCreateRequestDto(
        Double height,
        Double chest,
        Double arm
) {}