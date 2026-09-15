package com.healthmate.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.healthmate.model.DailyLog;

public interface DailyLogRepository extends JpaRepository<DailyLog, String> {
    List<DailyLog> findByUserIdOrderByDateAsc(String userId);

    Optional<DailyLog> findByUserIdAndDate(String userId, LocalDate date);
}
