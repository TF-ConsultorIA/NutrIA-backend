package com.nutria.nutria_api.profile.repository;

import com.nutria.nutria_api.profile.model.UserWeight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWeightRepository extends JpaRepository<UserWeight, Long> {
    List<UserWeight> findAllByUserIdOrderByRegisterDateDesc(Long userId);
}