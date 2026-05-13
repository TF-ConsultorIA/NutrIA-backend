package com.nutria.nutria_api.profile.model;

import com.nutria.nutria_api.auth.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_weitghts")
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class UserWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "register_date", nullable = false)
    private LocalDate registerDate;

    @Column(name = "weight_kg", nullable = false)
    private Double weightKg;
}