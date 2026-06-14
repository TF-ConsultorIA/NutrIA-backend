package com.nutria.nutria_api.profile.service.impl;

import com.nutria.nutria_api.auth.model.User;
import com.nutria.nutria_api.auth.repository.UserRepository;
import com.nutria.nutria_api.profile.dto.UserWeightCreateRequestDto;
import com.nutria.nutria_api.profile.dto.UserWeightResponseDto;
import com.nutria.nutria_api.profile.exception.UserWeightAlreadyRegisteredException;
import com.nutria.nutria_api.profile.mapper.ProfileMapper;
import com.nutria.nutria_api.profile.model.UserWeight;
import com.nutria.nutria_api.profile.repository.UserWeightRepository;
import com.nutria.nutria_api.profile.service.IUserWeightService;
import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserWeightService implements IUserWeightService {

    private final UserWeightRepository userWeightRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserWeightResponseDto> getWeights(Long userId) {
        return userWeightRepository.findAllByUserIdOrderByRegisterDateDesc(userId)
                .stream()
                .map(ProfileMapper::toUserWeightResponseDto)
                .toList();
    }

    // US-W01 : Consultar peso del día actual
    @Override
    @Transactional(readOnly = true)
    public Optional<UserWeightResponseDto> getWeightToday(Long userId) {
        return userWeightRepository.findByUserIdAndRegisterDate(userId, LocalDate.now())
                .map(ProfileMapper::toUserWeightResponseDto);
    }

    // US-W02 : Registrar peso
    @Override
    @Transactional
    public UserWeightResponseDto createWeight(Long userId, UserWeightCreateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (userWeightRepository.existsByUserIdAndRegisterDate(userId, request.registerDate())) {
            throw new UserWeightAlreadyRegisteredException(request.registerDate());
        }

        UserWeight weight = UserWeight.builder()
                .user(user)
                .registerDate(request.registerDate())
                .weightKg(request.weightKg())
                .build();

        return ProfileMapper.toUserWeightResponseDto(userWeightRepository.save(weight));
    }

    // US-W03 : Editar peso registrado
    @Override
    @Transactional
    public UserWeightResponseDto updateWeight(Long userId, Long weightId, UserWeightCreateRequestDto request) {
        UserWeight weight = userWeightRepository.findById(weightId)
                .orElseThrow(() -> new ResourceNotFoundException("Registro de peso no encontrado"));

        if (!weight.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Registro de peso no encontrado");
        }

        weight.setWeightKg(request.weightKg());
        weight.setRegisterDate(request.registerDate());

        return ProfileMapper.toUserWeightResponseDto(userWeightRepository.save(weight));
    }
}
