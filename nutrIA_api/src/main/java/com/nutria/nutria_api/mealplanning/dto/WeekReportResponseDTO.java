package com.nutria.nutria_api.mealplanning.dto;

import java.time.LocalDate;
import java.util.List;

public record WeekReportResponseDTO(
        Long weekId,
        Integer week,
        Integer year,
        LocalDate startDate,
        LocalDate endDate,
        List<DayReportDTO> days
) {}