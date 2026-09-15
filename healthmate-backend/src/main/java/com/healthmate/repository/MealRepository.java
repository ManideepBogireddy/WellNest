package com.healthmate.repository;

import com.healthmate.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, String> {
    List<Meal> findByUserId(String userId);
}
