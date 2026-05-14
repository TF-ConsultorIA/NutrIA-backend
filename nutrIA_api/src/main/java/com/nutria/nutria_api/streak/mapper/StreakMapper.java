package com.nutria.nutria_api.streak.mapper;

import com.nutria.nutria_api.streak.dto.StreakRequestDTO;
import com.nutria.nutria_api.streak.dto.StreakResponseDTO;
import com.nutria.nutria_api.streak.model.Streak;
import org.springframework.stereotype.Component;

@Component
public class StreakMapper {

    public Streak toEntity(StreakRequestDTO dto) {
        return Streak.builder()
                .userId(dto.getUserId())
                .streakNumber(dto.getStreakNumber())
                .lastLogin(dto.getLastLogin())
                .build();
    }

    public StreakResponseDTO toDTO(Streak streak) {
        return StreakResponseDTO.builder()
                .id(streak.getId())
                .userId(streak.getUserId())
                .streakNumber(streak.getStreakNumber())
                .lastLogin(streak.getLastLogin())
                .build();
    }
}