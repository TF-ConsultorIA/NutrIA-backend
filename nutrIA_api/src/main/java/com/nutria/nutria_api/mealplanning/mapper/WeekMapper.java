package com.nutria.nutria_api.mealplanning.mapper;

import com.nutria.nutria_api.mealplanning.dto.WeekRequestDTO;
import com.nutria.nutria_api.mealplanning.dto.WeekResponseDTO;
import com.nutria.nutria_api.mealplanning.model.Week;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WeekMapper {
    Week toEntity(WeekRequestDTO dto);
    WeekResponseDTO toDTO(Week week);
}
