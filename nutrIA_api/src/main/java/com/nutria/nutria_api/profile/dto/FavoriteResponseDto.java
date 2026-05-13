package com.nutria.nutria_api.profile.dto;

import java.time.LocalDate;

public record FavoriteResponseDto(
        Long id,
        Long foodId,
        LocalDate addedDate
) {}