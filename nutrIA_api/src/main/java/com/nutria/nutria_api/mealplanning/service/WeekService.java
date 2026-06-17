package com.nutria.nutria_api.mealplanning.service;

import com.nutria.nutria_api.mealplanning.dto.WeekRequestDTO;
import com.nutria.nutria_api.mealplanning.dto.WeekResponseDTO;
import com.nutria.nutria_api.shared.pagination.PageResponse;
import org.springframework.data.domain.Pageable;

public interface WeekService {
    PageResponse<WeekResponseDTO> getAllWeeks(Pageable pageable);
    WeekResponseDTO getWeekById(Long id);
    WeekResponseDTO getCurrentWeek();
    WeekResponseDTO createWeek(WeekRequestDTO request);
}
