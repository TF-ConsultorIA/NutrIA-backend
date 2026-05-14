package com.nutria.nutria_api.profile.service.impl;

import com.nutria.nutria_api.auth.model.User;
import com.nutria.nutria_api.auth.repository.UserRepository;
import com.nutria.nutria_api.food.repository.FoodRepository;
import com.nutria.nutria_api.profile.dto.FavoriteCreateRequestDto;
import com.nutria.nutria_api.profile.dto.FavoriteResponseDto;
import com.nutria.nutria_api.profile.mapper.ProfileMapper;
import com.nutria.nutria_api.profile.model.Favorite;
import com.nutria.nutria_api.profile.repository.FavoriteRepository;
import com.nutria.nutria_api.profile.service.IFavoriteService;
import com.nutria.nutria_api.shared.exception.BusinessRuleException;
import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FavoriteService implements IFavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Override
    public Page<FavoriteResponseDto> getFavorites(Long userId, Pageable pageable) {
        return favoriteRepository.findAllByUserId(userId, pageable)
                .map(ProfileMapper::toFavoriteResponseDto);
    }

    @Override
    public FavoriteResponseDto addFavorite(Long userId, FavoriteCreateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!foodRepository.existsById(request.foodId())) {
            throw new ResourceNotFoundException("Food no encontrado");
        }

        favoriteRepository.findByUserIdAndFoodId(userId, request.foodId())
                .ifPresent(f -> { throw new BusinessRuleException("El alimento ya esta en favoritos"); });

        Long count = favoriteRepository.countByUserId(userId);
        if (count >= 100) {
            throw new BusinessRuleException("No puedes tener mas de 100 favoritos");
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .foodId(request.foodId())
                .addedDate(LocalDate.now())
                .build();

        return ProfileMapper.toFavoriteResponseDto(favoriteRepository.save(favorite));
    }

    @Override
    public void deleteFavorite(Long userId, Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new ResourceNotFoundException("Favorito no encontrado"));

        if (!favorite.getUser().getId().equals(userId)) {
            throw new BusinessRuleException("No puedes eliminar favoritos de otro usuario");
        }

        favoriteRepository.delete(favorite);
    }
}