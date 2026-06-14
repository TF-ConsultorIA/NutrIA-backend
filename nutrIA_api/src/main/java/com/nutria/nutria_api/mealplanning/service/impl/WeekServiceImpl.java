package com.nutria.nutria_api.mealplanning.service.impl;

import com.nutria.nutria_api.mealplanning.dto.WeekRequestDTO;
import com.nutria.nutria_api.mealplanning.dto.WeekResponseDTO;
import com.nutria.nutria_api.mealplanning.exception.WeekNotFoundException;
import com.nutria.nutria_api.mealplanning.mapper.WeekMapper;
import com.nutria.nutria_api.mealplanning.model.Week;
import com.nutria.nutria_api.mealplanning.repository.WeekRepository;
import com.nutria.nutria_api.mealplanning.service.WeekService;
import com.nutria.nutria_api.shared.pagination.PageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WeekServiceImpl implements WeekService {

    private final WeekRepository weekRepository;
    private final WeekMapper weekMapper;

    // Listar todas las semanas paginadas
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public PageResponse<WeekResponseDTO> getAllWeeks(Pageable pageable) {
        return PageResponse.from(
                weekRepository.findAll(pageable).map(weekMapper::toDTO)
        );
    }

    // Obtener semana por id
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public WeekResponseDTO getWeekById(Long id) {
        Week week = weekRepository.findById(id)
                .orElseThrow(() -> new WeekNotFoundException(id));
        return weekMapper.toDTO(week);
    }

    // Obtener la semana actual según la fecha de hoy
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public WeekResponseDTO getCurrentWeek() {
        LocalDate today = LocalDate.now();
        Week week = weekRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(today, today)
                .orElseThrow(() -> new WeekNotFoundException(0L));
        return weekMapper.toDTO(week);
    }

    // Crear semana (ADMIN)
    @Override
    @Transactional
    public WeekResponseDTO createWeek(WeekRequestDTO request) {
        Week week = weekMapper.toEntity(request);
        return weekMapper.toDTO(weekRepository.save(week));
    }
}