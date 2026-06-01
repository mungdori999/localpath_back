package com.mungdori.localpath.adapter.badges.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record VerifyVisitResponse(
        String spotName,
        OffsetDateTime verifiedAt,
        boolean alreadyVisited,
        List<String> newlyUnlockedBadgeKeys
) {
}
