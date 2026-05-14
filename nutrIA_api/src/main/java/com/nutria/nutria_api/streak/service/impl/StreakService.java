package com.nutria.nutria_api.streak.service;

import com.nutria.nutria_api.streak.dto.StreakRequestDTO;
import com.nutria.nutria_api.streak.dto.StreakResponseDTO;

public interface StreakService {
    StreakResponseDTO getStreakByUserId(Long userId);
    StreakResponseDTO createStreak(StreakRequestDTO request);
    StreakResponseDTO updateStreak(Long userId, StreakRequestDTO request);
    void deleteStreak(Long userId);
}