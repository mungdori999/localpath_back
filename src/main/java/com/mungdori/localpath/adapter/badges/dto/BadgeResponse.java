package com.mungdori.localpath.adapter.badges.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record BadgeResponse(
        String id,
        String name,
        String description,
        String emoji,
        String image,
        String region,
        boolean unlocked,
        OffsetDateTime unlockedAt,
        List<BadgeRequirementResponse> requirements,
        int completedCount,
        int totalCount,
        BadgeVisitProgressResponse visitProgress
) {
}
