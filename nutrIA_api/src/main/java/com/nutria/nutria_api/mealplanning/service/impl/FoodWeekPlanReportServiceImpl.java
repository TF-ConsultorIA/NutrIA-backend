package com.nutria.nutria_api.mealplanning.service.impl;

import com.nutria.nutria_api.mealplanning.dto.DayReportDTO;
import com.nutria.nutria_api.mealplanning.dto.FoodWeekPlanDetailResponseDTO;
import com.nutria.nutria_api.mealplanning.dto.WeekReportResponseDTO;
import com.nutria.nutria_api.mealplanning.dto.WeekResponseDTO;
import com.nutria.nutria_api.mealplanning.model.TimeDay;
import com.nutria.nutria_api.mealplanning.service.FoodWeekPlanReportService;
import com.nutria.nutria_api.mealplanning.service.FoodWeekPlanService;
import com.nutria.nutria_api.mealplanning.service.WeekService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodWeekPlanReportServiceImpl implements FoodWeekPlanReportService {

    private final WeekService weekService;
    private final FoodWeekPlanService foodWeekPlanService;

    // Reporte de la semana actual del usuario organizado por día y momento del día
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public WeekReportResponseDTO getCurrentWeekReport(Long userId) {
        WeekResponseDTO currentWeek = weekService.getCurrentWeek();

        List<FoodWeekPlanDetailResponseDTO> plans = foodWeekPlanService
                .getPlansByUserAndWeek(userId, currentWeek.id());

        List<DayReportDTO> days = currentWeek.startDate()
                .datesUntil(currentWeek.endDate().plusDays(1))
                .map(date -> buildDayReport(date, plans))
                .collect(Collectors.toList());

        return new WeekReportResponseDTO(
                currentWeek.id(),
                currentWeek.week(),
                currentWeek.year(),
                currentWeek.startDate(),
                currentWeek.endDate(),
                days
        );
    }

    private DayReportDTO buildDayReport(LocalDate date, List<FoodWeekPlanDetailResponseDTO> plans) {
        List<FoodWeekPlanDetailResponseDTO> desayuno = filterByDateAndTime(plans, date, TimeDay.Desayuno);
        List<FoodWeekPlanDetailResponseDTO> almuerzo = filterByDateAndTime(plans, date, TimeDay.Almuerzo);
        List<FoodWeekPlanDetailResponseDTO> cena     = filterByDateAndTime(plans, date, TimeDay.Cena);
        return new DayReportDTO(date, desayuno, almuerzo, cena);
    }

    private List<FoodWeekPlanDetailResponseDTO> filterByDateAndTime(
            List<FoodWeekPlanDetailResponseDTO> plans, LocalDate date, TimeDay timeDay) {
        return plans.stream()
                .filter(p -> p.date().equals(date) && p.timeDay().equals(timeDay))
                .toList();
    }
}