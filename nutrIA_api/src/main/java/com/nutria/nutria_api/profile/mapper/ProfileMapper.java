package com.nutria.nutria_api.profile.mapper;

import com.nutria.nutria_api.profile.dto.*;
import com.nutria.nutria_api.profile.model.*;

public class ProfileMapper {

    public static PreferenceItemDto toPreferenceItemDto(Preference preference) {
        return new PreferenceItemDto(
                preference.getId(),
                preference.getFoodId(),
                preference.getType()
        );
    }

    public static FavoriteResponseDto toFavoriteResponseDto(Favorite favorite) {
        return new FavoriteResponseDto(
                favorite.getId(),
                favorite.getFoodId(),
                favorite.getAddedDate()
        );
    }

    public static UserMetricResponseDto toUserMetricResponseDto(UserMetric metric) {
        return new UserMetricResponseDto(
                metric.getId(),
                metric.getHeight(),
                metric.getChest(),
                metric.getArm()
        );
    }

    public static UserWeightResponseDto toUserWeightResponseDto(UserWeight weight) {
        return new UserWeightResponseDto(
                weight.getId(),
                weight.getRegisterDate(),
                weight.getWeightKg()
        );
    }
}