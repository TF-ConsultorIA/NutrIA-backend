package com.nutria.nutria_api.profile.repository;

import com.nutria.nutria_api.profile.model.UserWeight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserWeightRepository extends JpaRepository<UserWeight, Long> {
    List<UserWeight> findAllByUserIdOrderByRegisterDateDesc(Long userId);
    Optional<UserWeight> findByUserIdAndRegisterDate(Long userId, LocalDate registerDate);
}