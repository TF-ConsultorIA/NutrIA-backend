package com.nutria.nutria_api.mealplanning.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "food_week_plans")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class FoodWeekPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "week_id", nullable = false)
    private Long weekId;

    @Column(name = "food_id", nullable = false)
    private Long foodId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "time_day", nullable = false)
    private TimeDay timeDay;

    @Column(nullable = false)
    private Double portion;
}
