package com.mungdori.localpath.adapter.survey.dto;

import java.util.Map;

public record SubmitSurveyRequest(Map<String, String> answers) {
}
