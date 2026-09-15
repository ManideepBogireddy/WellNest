package com.healthmate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.healthmate.model.User;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Optional<User> findByEmail(String email);
}
