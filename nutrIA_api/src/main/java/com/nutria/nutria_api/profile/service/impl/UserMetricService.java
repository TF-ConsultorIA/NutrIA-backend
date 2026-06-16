package com.nutria.nutria_api.profile.service.impl;

import com.nutria.nutria_api.auth.model.User;
import com.nutria.nutria_api.auth.repository.UserRepository;
import com.nutria.nutria_api.profile.dto.UserMetricCreateRequestDto;
import com.nutria.nutria_api.profile.dto.UserMetricResponseDto;
import com.nutria.nutria_api.profile.mapper.ProfileMapper;
import com.nutria.nutria_api.profile.model.UserMetric;
import com.nutria.nutria_api.profile.repository.UserMetricRepository;
import com.nutria.nutria_api.profile.service.IUserMetricService;
import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMetricService implements IUserMetricService {

    private final UserMetricRepository userMetricRepository;
    private final UserRepository userRepository;

    @Override
    public UserMetricResponseDto getMetrics(Long userId) {
        var metrics = userMetricRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("Metricas no encontradas")
        );

        return ProfileMapper.toUserMetricResponseDto(metrics);
    }

    @Override
    public UserMetricResponseDto upsertMetric(Long userId, UserMetricCreateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        UserMetric metric = userMetricRepository.findByUserId(userId)
                .map(existingMetric -> {
                    existingMetric.setHeight(request.height());
                    existingMetric.setChest(request.chest());
                    existingMetric.setArm(request.arm());
                    return existingMetric;
                })
                .orElseGet(() -> {
                    return UserMetric.builder()
                            .user(user)
                            .height(request.height())
                            .chest(request.chest())
                            .arm(request.arm())
                            .build();
                });

        UserMetric savedMetric = userMetricRepository.save(metric);

        return ProfileMapper.toUserMetricResponseDto(savedMetric);
    }
}