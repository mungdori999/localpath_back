package com.mungdori.localpath.adapter.badges.dto;

import java.util.List;

public record BadgeVisitProgressResponse(
        int visitedCount,
        int requiredVisits,
        int categoryCount,
        int requiredCategories,
        List<String> visitedCategories
) {
}
