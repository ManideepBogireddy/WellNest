package com.healthmate.repository;

import com.healthmate.model.DietPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DietPlanRepository extends JpaRepository<DietPlan, String> {
    List<DietPlan> findByTrainerId(String trainerId);
}
