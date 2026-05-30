package com.mungdori.localpath.adapter.badges.dto;

import java.time.LocalDateTime;
import java.util.List;

public record VisitResponse(
        String spotName,
        LocalDateTime verifiedAt
) {
}
