package com.mungdori.localpath.adapter.survey.dto;

import java.util.List;

public record SurveyStatusResponse(
        boolean completed,
        List<TravelTypeScoreResponse> typeScores,
        PassRecommendationResponse recommendation
) {
}
