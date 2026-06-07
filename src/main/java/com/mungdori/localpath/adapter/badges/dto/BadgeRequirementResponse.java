package com.mungdori.localpath.adapter.badges.dto;

public record BadgeRequirementResponse(
        String spotName,
        String category,
        boolean completed
) {
}
