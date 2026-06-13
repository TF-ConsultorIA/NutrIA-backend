package com.nutria.nutria_api.streak.mapper;

import com.nutria.nutria_api.streak.dto.StreakRequestDTO;
import com.nutria.nutria_api.streak.dto.StreakResponseDTO;
import com.nutria.nutria_api.streak.model.Streak;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StreakMapper {
    Streak toEntity(StreakRequestDTO dto);
    StreakResponseDTO toDTO(Streak streak);
}