package com.nutria.nutria_api.profile.service;

import com.nutria.nutria_api.profile.dto.*;

import java.util.List;

public interface IPreferenceService {
    PreferenceResponseDto getPreferences(Long userId);
    PreferenceResponseDto updatePreferences(Long userId, PreferenceUpdateRequestDto request);
    PreferenceItemDto addPreference(Long userId, PreferenceCreateRequestDto request);
    List<PreferenceItemDto> getMyTypePreference(String type);
    void deletePreference(Long userId, Long preferenceId);
}