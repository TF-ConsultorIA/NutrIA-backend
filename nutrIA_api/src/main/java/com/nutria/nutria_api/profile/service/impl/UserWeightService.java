package com.nutria.nutria_api.profile.service.impl;

import com.nutria.nutria_api.auth.model.User;
import com.nutria.nutria_api.auth.repository.UserRepository;
import com.nutria.nutria_api.profile.dto.UserWeightCreateRequestDto;
import com.nutria.nutria_api.profile.dto.UserWeightResponseDto;
import com.nutria.nutria_api.profile.mapper.ProfileMapper;
import com.nutria.nutria_api.profile.model.UserWeight;
import com.nutria.nutria_api.profile.repository.UserWeightRepository;
import com.nutria.nutria_api.profile.service.IUserWeightService;
import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserWeightService implements IUserWeightService {

    private final UserWeightRepository userWeightRepository;
    private final UserRepository userRepository;

    @Override
    public List<UserWeightResponseDto> getWeights(Long userId) {
        return userWeightRepository.findAllByUserIdOrderByRegisterDateDesc(userId)
                .stream()
                .map(ProfileMapper::toUserWeightResponseDto)
                .toList();
    }

    @Override
    public UserWeightResponseDto createWeight(Long userId, UserWeightCreateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        UserWeight weight = UserWeight.builder()
                .user(user)
                .registerDate(request.registerDate())
                .weightKg(request.weightKg())
                .build();

        return ProfileMapper.toUserWeightResponseDto(userWeightRepository.save(weight));
    }
}