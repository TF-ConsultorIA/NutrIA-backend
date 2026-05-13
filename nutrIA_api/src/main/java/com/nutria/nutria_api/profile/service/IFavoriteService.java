package com.nutria.nutria_api.profile.service;

import com.nutria.nutria_api.profile.dto.FavoriteCreateRequestDto;
import com.nutria.nutria_api.profile.dto.FavoriteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFavoriteService {
    Page<FavoriteResponseDto> getFavorites(Long userId, Pageable pageable);
    FavoriteResponseDto addFavorite(Long userId, FavoriteCreateRequestDto request);
    void deleteFavorite(Long userId, Long favoriteId);
}