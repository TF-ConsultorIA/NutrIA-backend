package com.nutria.nutria_api.profile.model;

import com.nutria.nutria_api.auth.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "favorites")
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "food_id", nullable = false)
    private Long foodId;

    @Column(name = "added_date", nullable = false)
    private LocalDate addedDate;
}