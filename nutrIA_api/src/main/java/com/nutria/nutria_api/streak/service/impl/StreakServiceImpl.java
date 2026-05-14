package com.nutria.nutria_api.streak.service.impl;

import com.nutria.nutria_api.streak.dto.StreakRequestDTO;
import com.nutria.nutria_api.streak.dto.StreakResponseDTO;
import com.nutria.nutria_api.streak.exception.StreakNotFoundException;
import com.nutria.nutria_api.streak.mapper.StreakMapper;
import com.nutria.nutria_api.streak.model.Streak;
import com.nutria.nutria_api.streak.repository.StreakRepository;
import com.nutria.nutria_api.streak.service.StreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StreakServiceImpl implements StreakService {

    private final StreakRepository streakRepository;
    private final StreakMapper streakMapper;

    @Override
    public StreakResponseDTO getStreakByUserId(Long userId) {
        Streak streak = streakRepository.findByUserId(userId)
                .orElseThrow(() -> new StreakNotFoundException(userId));
        return streakMapper.toDTO(streak);
    }

    @Override
    public StreakResponseDTO createStreak(StreakRequestDTO request) {
        Streak streak = streakMapper.toEntity(request);
        return streakMapper.toDTO(streakRepository.save(streak));
    }

    @Override
    public StreakResponseDTO updateStreak(Long userId, StreakRequestDTO request) {
        Streak streak = streakRepository.findByUserId(userId)
                .orElseThrow(() -> new StreakNotFoundException(userId));
        streak.setStreakNumber(request.getStreakNumber());
        streak.setLastLogin(request.getLastLogin());
        return streakMapper.toDTO(streakRepository.save(streak));
    }

    @Override
    public void deleteStreak(Long userId) {
        Streak streak = streakRepository.findByUserId(userId)
                .orElseThrow(() -> new StreakNotFoundException(userId));
        streakRepository.delete(streak);
    }
}