package com.nutria.nutria_api.profile.service;

import com.nutria.nutria_api.profile.dto.UserMetricCreateRequestDto;
import com.nutria.nutria_api.profile.dto.UserMetricResponseDto;

import java.util.List;

public interface IUserMetricService {
    List<UserMetricResponseDto> getMetrics(Long userId);
    UserMetricResponseDto createMetric(Long userId, UserMetricCreateRequestDto request);
}