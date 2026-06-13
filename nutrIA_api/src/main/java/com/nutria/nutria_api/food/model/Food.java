package com.nutria.nutria_api.food.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_name", nullable = false)
    private String foodName;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_type", nullable = false)
    private FoodType foodType;

    @Column(nullable = false)
    private Double energy;

    @Column(nullable = false)
    private Double proteins;

    @Column(name = "total_fat", nullable = false)
    private Double totalFat;

    @Column(nullable = false)
    private Double water;

    @Column(name = "carbohydrates_total", nullable = false)
    private Double carbohydratesTotal;

    @Column(nullable = false)
    private Double calcium;

    @Column(nullable = false)
    private Double iron;

    @Column(nullable = false)
    private Double sodium;
}