package com.mungdori.localpath.application.survey;

import com.mungdori.localpath.domain.survey.TravelType;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class SurveyScoringService {

    private final Map<String, Map<TravelType, Integer>> weights = SurveyQuestions.optionWeights();

    public EnumMap<TravelType, Integer> score(Map<String, String> answers) {
        EnumMap<TravelType, Integer> scores = new EnumMap<>(TravelType.class);
        for (TravelType type : TravelType.values()) {
            scores.put(type, 0);
        }

        for (String questionId : SurveyQuestions.QUESTION_IDS) {
            String optionId = answers.get(questionId);
            if (optionId == null || optionId.isBlank()) {
                continue;
            }
            Map<TravelType, Integer> optionWeight =
                    weights.get(SurveyQuestions.compositeKey(questionId, optionId));
            if (optionWeight == null) {
                continue;
            }
            optionWeight.forEach((type, value) ->
                    scores.merge(type, value, Integer::sum));
        }

        return scores;
    }

    public TravelType primaryType(EnumMap<TravelType, Integer> scores) {
        return scores.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(TravelType.HEALING);
    }

    public List<TravelType> rankedTypes(EnumMap<TravelType, Integer> scores) {
        return scores.entrySet().stream()
                .sorted(Map.Entry.<TravelType, Integer>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .toList();
    }
}
