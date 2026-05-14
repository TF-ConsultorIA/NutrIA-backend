package com.nutria.nutria_api.profile.repository;

import com.nutria.nutria_api.profile.model.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Page<Favorite> findAllByUserId(Long userId, Pageable pageable);
    Optional<Favorite> findByUserIdAndFoodId(Long userId, Long foodId);
    Long countByUserId(Long userId);
}