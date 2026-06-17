package com.nutria.nutria_api.mealplanning.dto;


import java.time.LocalDate;

public record WeekResponseDTO(
        Long id,
        Integer week,
        Integer year,
        LocalDate startDate,
        LocalDate endDate
) {}
