package com.mungdori.localpath.application.badges.required;

import com.mungdori.localpath.domain.badges.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Long> {

    Optional<Badge> findByBadgeKey(String badgeKey);

    @Query("SELECT b FROM Badge b LEFT JOIN FETCH b.requirements ORDER BY b.orderIndex")
    List<Badge> findAllWithRequirements();
}
