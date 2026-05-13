package com.nutria.nutria_api.profile.dto;

import java.util.List;

public record PreferenceResponseDto(
        List<PreferenceItemDto> liked,
        List<PreferenceItemDto> disliked,
        List<PreferenceItemDto> allergies
) {}