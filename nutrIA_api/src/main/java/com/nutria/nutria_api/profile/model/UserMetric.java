package com.nutria.nutria_api.profile.model;

import com.nutria.nutria_api.auth.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_metrics")
@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class UserMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "heigh", nullable = false)
    private Double height;

    @Column(name = "chest")
    private Double chest;

    @Column(name = "arm")
    private Double arm;
}