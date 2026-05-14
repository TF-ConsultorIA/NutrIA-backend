package com.nutria.nutria_api.profile.service;

import com.nutria.nutria_api.profile.dto.*;

public interface IPreferenceService {
    PreferenceResponseDto getPreferences(Long userId);
    PreferenceResponseDto updatePreferences(Long userId, PreferenceUpdateRequestDto request);
    PreferenceItemDto addPreference(Long userId, PreferenceCreateRequestDto request);
    void deletePreference(Long userId, Long preferenceId);
}