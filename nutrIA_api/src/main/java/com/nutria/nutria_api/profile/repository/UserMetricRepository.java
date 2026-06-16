package com.nutria.nutria_api.profile.repository;

import com.nutria.nutria_api.profile.model.UserMetric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMetricRepository extends JpaRepository<UserMetric, Long> {
    Optional<UserMetric> findByUserId(Long userId);
}