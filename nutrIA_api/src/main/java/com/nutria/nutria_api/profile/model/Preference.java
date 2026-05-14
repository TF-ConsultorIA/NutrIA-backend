package com.nutria.nutria_api.profile.model;

import com.nutria.nutria_api.auth.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "preferences")
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "food_id", nullable = false)
    private Long foodId;

    @Column(name = "type", nullable = false)
    private String type;
}