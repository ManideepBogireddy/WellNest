package com.healthmate.repository;

import com.healthmate.model.Follow;
import com.healthmate.enums.FollowType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, String> {
    List<Follow> findByFollowerId(String followerId);
    List<Follow> findByFollowerIdAndType(String followerId, FollowType type);
    Optional<Follow> findByFollowerIdAndTargetIdAndType(String followerId, String targetId, FollowType type);
    boolean existsByFollowerIdAndTargetIdAndType(String followerId, String targetId, FollowType type);
    @Transactional
    void deleteByFollowerIdAndTargetIdAndType(String followerId, String targetId, FollowType type);
    long countByTargetIdAndType(String targetId, FollowType type);
    List<Follow> findByTargetIdAndType(String targetId, FollowType type);
}
