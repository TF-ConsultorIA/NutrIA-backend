package com.nutria.nutria_api.streak.service.impl;

import com.nutria.nutria_api.streak.dto.StreakRequestDTO;
import com.nutria.nutria_api.streak.dto.StreakResponseDTO;
import com.nutria.nutria_api.streak.exception.StreakNotFoundException;
import com.nutria.nutria_api.streak.mapper.StreakMapper;
import com.nutria.nutria_api.streak.model.Streak;
import com.nutria.nutria_api.streak.repository.StreakRepository;
import com.nutria.nutria_api.streak.service.StreakService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class StreakServiceImpl implements StreakService {

    private final StreakRepository streakRepository;
    private final StreakMapper streakMapper;

    // US-25 : Consultar racha de uso por usuario
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public StreakResponseDTO getStreakByUserId(Long userId) {
        Streak streak = streakRepository.findByUserId(userId)
                .orElseThrow(() -> new StreakNotFoundException(userId));
        return streakMapper.toDTO(streak);
    }

    // US-25 : Registrar racha de uso (crea streak inicial con streakNumber = 0)
    @Override
    @Transactional
    public StreakResponseDTO createStreak(StreakRequestDTO request) {
        Streak streak = streakMapper.toEntity(request);
        return streakMapper.toDTO(streakRepository.save(streak));
    }

    // US-25 : Actualizar racha manualmente (solo ADMIN)
    @Override
    @Transactional
    public StreakResponseDTO updateStreak(Long userId, StreakRequestDTO request) {
        Streak streak = streakRepository.findByUserId(userId)
                .orElseThrow(() -> new StreakNotFoundException(userId));
        streak.setStreakNumber(request.streakNumber());
        streak.setLastLogin(request.lastLogin());
        return streakMapper.toDTO(streakRepository.save(streak));
    }

    // US-25 : Check-in diario — aplica lógica real de racha
    // Reglas:
    //   daysDiff == 0  → ya entró hoy, sin cambios
    //   daysDiff == 1  → entró ayer, suma 1
    //   daysDiff >= 2  → perdió 2+ días, reinicia a 1 (hoy cuenta como día 1)
    @Override
    @Transactional
    public StreakResponseDTO checkIn(Long userId) {
        Streak streak = streakRepository.findByUserId(userId)
                .orElseThrow(() -> new StreakNotFoundException(userId));

        LocalDate today    = LocalDate.now();
        LocalDate lastLogin = streak.getLastLogin();
        long daysDiff      = ChronoUnit.DAYS.between(lastLogin, today);

        if (daysDiff == 0) {
            // Ya hizo check-in hoy, retorna sin cambios
            return streakMapper.toDTO(streak);
        } else if (daysDiff == 1) {
            // Entró ayer → suma racha
            streak.setStreakNumber(streak.getStreakNumber() + 1);
        } else {
            // Perdió 2+ días → reinicia, hoy es día 1
            streak.setStreakNumber(1);
        }

        streak.setLastLogin(today);
        return streakMapper.toDTO(streakRepository.save(streak));
    }

    // US-25 : Eliminar racha de uso
    @Override
    @Transactional
    public void deleteStreak(Long userId) {
        Streak streak = streakRepository.findByUserId(userId)
                .orElseThrow(() -> new StreakNotFoundException(userId));
        streakRepository.delete(streak);
    }
}
