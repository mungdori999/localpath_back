package com.mungdori.localpath.adapter.badges.dto;

import java.time.OffsetDateTime;

public record VisitResponse(
        String spotName,
        OffsetDateTime verifiedAt
) {
}
