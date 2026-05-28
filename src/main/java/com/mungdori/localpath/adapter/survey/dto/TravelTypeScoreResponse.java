package com.mungdori.localpath.adapter.survey.dto;

public record TravelTypeScoreResponse(
        String type,
        String courseKey,
        String label,
        String description,
        int score
) {
}
