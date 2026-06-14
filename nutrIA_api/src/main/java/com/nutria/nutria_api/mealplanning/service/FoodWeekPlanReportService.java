package com.nutria.nutria_api.mealplanning.service;

import com.nutria.nutria_api.mealplanning.dto.WeekReportResponseDTO;

public interface FoodWeekPlanReportService {
    WeekReportResponseDTO getCurrentWeekReport(Long userId);
}