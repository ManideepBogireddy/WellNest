package com.healthmate.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.healthmate.model.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, String> {
    Optional<Trainer> findByUserId(String userId);
}
