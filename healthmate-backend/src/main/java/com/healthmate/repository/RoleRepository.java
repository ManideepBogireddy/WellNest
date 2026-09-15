package com.healthmate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.healthmate.model.ERole;
import com.healthmate.model.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
  Optional<Role> findByName(ERole name);
}
