package com.nutria.nutria_api.profile.service;

import com.nutria.nutria_api.profile.dto.UserWeightCreateRequestDto;
import com.nutria.nutria_api.profile.dto.UserWeightResponseDto;

import java.util.List;
import java.util.Optional;

public interface IUserWeightService {
    List<UserWeightResponseDto> getWeights(Long userId);
    Optional<UserWeightResponseDto> getWeightToday(Long userId);
    UserWeightResponseDto createWeight(Long userId, UserWeightCreateRequestDto request);
    UserWeightResponseDto updateWeight(Long userId, Long weightId, UserWeightCreateRequestDto request);
}
