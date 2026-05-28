package com.mungdori.localpath.adapter.survey.dto;

public record PassRecommendationResponse(
        String passId,
        String passName,
        String passImage,
        String courseKey,
        String courseName,
        String courseEmoji,
        String primaryType,
        String primaryTypeLabel,
        String reason
) {
}
