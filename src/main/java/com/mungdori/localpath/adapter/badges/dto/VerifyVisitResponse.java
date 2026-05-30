package com.mungdori.localpath.adapter.badges.dto;

import java.time.LocalDateTime;
import java.util.List;

public record VerifyVisitResponse(
        String spotName,
        LocalDateTime verifiedAt,
        boolean alreadyVisited,
        List<String> newlyUnlockedBadgeIds
) {
}
