package com.healthmate.repository;

import com.healthmate.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, String> {
    List<WorkoutPlan> findByTrainerId(String trainerId);
}
