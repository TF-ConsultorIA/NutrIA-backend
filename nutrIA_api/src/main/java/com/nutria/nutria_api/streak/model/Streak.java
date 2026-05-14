package com.nutria.nutria_api.streak.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "streak")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Streak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "streak_number", nullable = false)
    private Integer streakNumber;

    @Column(name = "last_login", nullable = false)
    private LocalDate lastLogin;
}