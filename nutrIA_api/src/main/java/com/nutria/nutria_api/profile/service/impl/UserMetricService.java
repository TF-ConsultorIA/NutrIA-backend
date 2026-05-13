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
    public List<UserMetricResponseDto> getMetrics(Long userId) {
        return userMetricRepository.findAllByUserIdOrderByIdDesc(userId)
                .stream()
                .map(ProfileMapper::toUserMetricResponseDto)
                .toList();
    }

    @Override
    public UserMetricResponseDto createMetric(Long userId, UserMetricCreateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        UserMetric metric = UserMetric.builder()
                .user(user)
                .height(request.height())
                .chest(request.chest())
                .arm(request.arm())
                .build();

        return ProfileMapper.toUserMetricResponseDto(userMetricRepository.save(metric));
    }
}