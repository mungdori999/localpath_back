package com.mungdori.localpath.adapter.survey.dto;

import java.util.List;

public record HomeResponse(
        boolean loggedIn,
        boolean surveyCompleted,
        List<TravelTypeScoreResponse> typeScores,
        PassRecommendationResponse recommendation
) {
}
