package com.nutria.nutria_api.streak.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreakRequestDTO {
    private Long userId;
    private Integer streakNumber;
    private LocalDate lastLogin;
}