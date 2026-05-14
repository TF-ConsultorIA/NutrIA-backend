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

    @Column(name = "food_type", nullable = false)
    private String foodType;

    @Column()
    private Double energy;

    @Column()
    private Double proteins;

    @Column(name = "total_fat")
    private Double totalFat;

    @Column()
    private Double water;

    @Column(name = "carbohydrates_total")
    private Double carbohydratesTotal;

    @Column()
    private Double calcium;

    @Column()
    private Double iron;

    @Column()
    private Double sodium;
}