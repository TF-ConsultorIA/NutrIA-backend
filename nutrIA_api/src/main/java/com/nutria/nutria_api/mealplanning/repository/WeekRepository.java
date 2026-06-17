package com.nutria.nutria_api.mealplanning.repository;

import com.nutria.nutria_api.mealplanning.model.Week;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WeekRepository extends JpaRepository<Week, Long> {
    // Busca la semana cuyo rango contiene la fecha dada → semana actual
    Optional<Week> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate date1, LocalDate date2);
}