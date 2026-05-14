package com.nutria.nutria_api.streak.controller;

import com.nutria.nutria_api.streak.dto.StreakRequestDTO;
import com.nutria.nutria_api.streak.dto.StreakResponseDTO;
import com.nutria.nutria_api.streak.service.StreakService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Streak")
@RestController
@RequestMapping("/api/v1/streaks")
@RequiredArgsConstructor
public class StreakController {

    private final StreakService streakService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<StreakResponseDTO> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(streakService.getStreakByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<StreakResponseDTO> create(@RequestBody StreakRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(streakService.createStreak(request));
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<StreakResponseDTO> update(@PathVariable Long userId,
                                                    @RequestBody StreakRequestDTO request) {
        return ResponseEntity.ok(streakService.updateStreak(userId, request));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {
        streakService.deleteStreak(userId);
        return ResponseEntity.noContent().build();
    }
}