package com.nutria.nutria_api.profile.repository;

import com.nutria.nutria_api.profile.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    List<Preference> findAllByUserId(Long userId);
    Optional<Preference> findByUserIdAndFoodIdAndType(Long userId, Long foodId, String type);
    Long countByUserIdAndTypeIn(Long userId, List<String> types);
}