package com.nutria.nutria_api.profile.repository;

import com.nutria.nutria_api.profile.model.UserMetric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMetricRepository extends JpaRepository<UserMetric, Long> {
    List<UserMetric> findAllByUserIdOrderByIdDesc(Long userId);
}