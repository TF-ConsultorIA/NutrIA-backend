package com.nutria.nutria_api.profile.service.impl;

import com.nutria.nutria_api.auth.model.User;
import com.nutria.nutria_api.auth.repository.UserRepository;
import com.nutria.nutria_api.food.repository.FoodRepository;
import com.nutria.nutria_api.profile.dto.*;
import com.nutria.nutria_api.profile.mapper.ProfileMapper;
import com.nutria.nutria_api.profile.model.Preference;
import com.nutria.nutria_api.profile.repository.PreferenceRepository;
import com.nutria.nutria_api.profile.service.IPreferenceService;
import com.nutria.nutria_api.shared.exception.BusinessRuleException;
import com.nutria.nutria_api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PreferenceService implements IPreferenceService {

    private final PreferenceRepository preferenceRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Override
    public PreferenceResponseDto getPreferences(Long userId) {
        List<Preference> preferences = preferenceRepository.findAllByUserId(userId);

        List<PreferenceItemDto> liked = new ArrayList<>();
        List<PreferenceItemDto> disliked = new ArrayList<>();
        List<PreferenceItemDto> allergies = new ArrayList<>();

        for (Preference p : preferences) {
            PreferenceItemDto dto = ProfileMapper.toPreferenceItemDto(p);
            switch (p.getType().toLowerCase()) {
                case "liked" -> liked.add(dto);
                case "disliked" -> disliked.add(dto);
                case "allergy", "allergies" -> allergies.add(dto);
                default -> throw new BusinessRuleException("Tipo de preferencia invalido: " + p.getType());
            }
        }
        return new PreferenceResponseDto(liked, disliked, allergies);
    }

    @Override
    public PreferenceResponseDto updatePreferences(Long userId, PreferenceUpdateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Set<Long> liked = new HashSet<>(request.liked());
        Set<Long> disliked = new HashSet<>(request.disliked());
        Set<Long> allergies = new HashSet<>(request.allergies());

        if (!Collections.disjoint(liked, disliked) ||
                !Collections.disjoint(liked, allergies) ||
                !Collections.disjoint(disliked, allergies)) {
            throw new BusinessRuleException("Un alimento no puede estar en listas conflictivas");
        }

        int exclusions = disliked.size() + allergies.size();
        if (exclusions > 40) {
            throw new BusinessRuleException("No puedes tener mas de 40 exclusiones entre desagrados y alergias");
        }

        for (Long foodId : liked) {
            if (!foodRepository.existsById(foodId)) {
                throw new ResourceNotFoundException("Food no encontrado: " + foodId);
            }
        }
        for (Long foodId : disliked) {
            if (!foodRepository.existsById(foodId)) {
                throw new ResourceNotFoundException("Food no encontrado: " + foodId);
            }
        }
        for (Long foodId : allergies) {
            if (!foodRepository.existsById(foodId)) {
                throw new ResourceNotFoundException("Food no encontrado: " + foodId);
            }
        }

        preferenceRepository.deleteAll(preferenceRepository.findAllByUserId(userId));

        List<Preference> toSave = new ArrayList<>();
        liked.forEach(foodId -> toSave.add(buildPreference(user, foodId, "liked")));
        disliked.forEach(foodId -> toSave.add(buildPreference(user, foodId, "disliked")));
        allergies.forEach(foodId -> toSave.add(buildPreference(user, foodId, "allergy")));

        preferenceRepository.saveAll(toSave);

        return getPreferences(userId);
    }

    @Override
    public PreferenceItemDto addPreference(Long userId, PreferenceCreateRequestDto request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        if (!foodRepository.existsById(request.foodId())) {
            throw new ResourceNotFoundException("Food no encontrado");
        }

        preferenceRepository.findByUserIdAndFoodIdAndType(userId, request.foodId(), request.type())
                .ifPresent(p -> { throw new BusinessRuleException("Preferencia ya registrada"); });

        Preference pref = buildPreference(user, request.foodId(), request.type());
        return ProfileMapper.toPreferenceItemDto(preferenceRepository.save(pref));
    }

    @Override
    public void deletePreference(Long userId, Long preferenceId) {
        Preference pref = preferenceRepository.findById(preferenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Preferencia no encontrada"));

        if (!pref.getUser().getId().equals(userId)) {
            throw new BusinessRuleException("No puedes eliminar preferencias de otro usuario");
        }

        preferenceRepository.delete(pref);
    }

    private Preference buildPreference(User user, Long foodId, String type) {
        return Preference.builder()
                .user(user)
                .foodId(foodId)
                .type(type)
                .build();
    }
}