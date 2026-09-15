package com.healthmate.repository;

import com.healthmate.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, String> {
    List<Workout> findByUserId(String userId);
}