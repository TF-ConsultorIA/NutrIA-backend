package com.nutria.nutria_api.streak.repository;

import com.nutria.nutria_api.streak.model.Streak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StreakRepository extends JpaRepository<Streak, Long> {
    Optional<Streak> findByUserId(Long userId);
}