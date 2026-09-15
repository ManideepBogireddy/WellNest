package com.healthmate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.healthmate.model.ModerationLog;

public interface ModerationLogRepository extends JpaRepository<ModerationLog, String> {
}
