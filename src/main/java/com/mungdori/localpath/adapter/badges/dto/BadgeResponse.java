package com.mungdori.localpath.adapter.badges.dto;

import java.time.LocalDateTime;
import java.util.List;

public record BadgeResponse(
        String id,
        String name,
        String description,
        String emoji,
        String region,
        boolean unlocked,
        LocalDateTime unlockedAt,
        List<BadgeRequirementResponse> requirements,
        int completedCount,
        int totalCount
) {
}
