package com.healthmate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.healthmate.model.HealthPlan;

public interface HealthPlanRepository extends JpaRepository<HealthPlan, String> {
    Optional<HealthPlan> findByUserId(String userId);
}
