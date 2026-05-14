package com.nutria.nutria_api.food.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodRequestDTO {
    private String foodName;
    private String foodType;
    private Double energy;
    private Double proteins;
    private Double totalFat;
    private Double water;
    private Double carbohydratesTotal;
    private Double calcium;
    private Double iron;
    private Double sodium;
}