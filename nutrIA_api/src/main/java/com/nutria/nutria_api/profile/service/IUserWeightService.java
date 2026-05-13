package com.nutria.nutria_api.profile.service;

import com.nutria.nutria_api.profile.dto.UserWeightCreateRequestDto;
import com.nutria.nutria_api.profile.dto.UserWeightResponseDto;

import java.util.List;

public interface IUserWeightService {
    List<UserWeightResponseDto> getWeights(Long userId);
    UserWeightResponseDto createWeight(Long userId, UserWeightCreateRequestDto request);
}