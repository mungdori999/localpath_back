package com.mungdori.localpath.adapter.survey.dto;

import java.util.List;

public record SurveyQuestionResponse(
        String id,
        String text,
        List<SurveyOptionResponse> options
) {
}
