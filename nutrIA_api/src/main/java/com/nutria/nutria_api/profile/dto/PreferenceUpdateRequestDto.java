package com.nutria.nutria_api.profile.dto;

import java.util.List;

public record PreferenceUpdateRequestDto(
        List<Long> liked,
        List<Long> disliked,
        List<Long> allergies
) {}