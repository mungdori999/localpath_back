package com.mungdori.localpath.application.survey;

import com.mungdori.localpath.adapter.survey.dto.SurveyOptionResponse;
import com.mungdori.localpath.adapter.survey.dto.SurveyQuestionResponse;
import com.mungdori.localpath.domain.survey.TravelType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 설문 문항 정의 (프론트는 GET /api/survey/questions 로 동일 내용 수신)
 */
public final class SurveyQuestions {

    private SurveyQuestions() {
    }

    public static final List<String> QUESTION_IDS = List.of(
            "trip_goal", "pace", "place", "food_interest", "day_style"
    );

    public static List<SurveyQuestionResponse> all() {
        return List.of(
                question(
                        "trip_goal",
                        "이번 망원 여행에서 가장 원하는 것은?",
                        List.of(
                                option("rest", "조용히 쉬고 싶어요"),
                                option("eat", "맛집·먹거리가 최고예요"),
                                option("culture", "문화·로컬 체험이 하고 싶어요"),
                                option("night_view", "야경·한강 산책이 좋아요")
                        )
                ),
                question(
                        "pace",
                        "걷는 페이스는 어떤가요?",
                        List.of(
                                option("slow", "천천히 여유롭게"),
                                option("normal", "적당히 걸어요"),
                                option("active", "활동적으로 많이 걸어요")
                        )
                ),
                question(
                        "place",
                        "가장 끌리는 장소는?",
                        List.of(
                                option("cafe", "분위기 좋은 카페"),
                                option("restaurant", "동네 맛집"),
                                option("market", "시장·골목 먹거리"),
                                option("culture", "전시·문화 공간"),
                                option("riverside", "한강·야경 스팟")
                        )
                ),
                question(
                        "food_interest",
                        "먹거리에 대한 관심은?",
                        List.of(
                                option("low", "가볍게만 먹을래요"),
                                option("medium", "맛집 1~2곳 정도"),
                                option("high", "먹거리가 여행의 핵심이에요")
                        )
                ),
                question(
                        "day_style",
                        "하루 일정 스타일은?",
                        List.of(
                                option("light", "가볍게 2~3곳만"),
                                option("standard", "코스대로 차근차근"),
                                option("full", "하루 종일 알차게")
                        )
                )
        );
    }

    /**
     * questionId:optionId → 유형별 가중치
     */
    public static Map<String, Map<TravelType, Integer>> optionWeights() {
        Map<String, Map<TravelType, Integer>> weights = new HashMap<>();

        w(weights, "trip_goal", "rest", 3, 0, 0, 0);
        w(weights, "trip_goal", "eat", 0, 3, 0, 0);
        w(weights, "trip_goal", "culture", 0, 0, 3, 0);
        w(weights, "trip_goal", "night_view", 0, 0, 0, 3);

        w(weights, "pace", "slow", 2, 0, 0, 0);
        w(weights, "pace", "normal", 1, 1, 0, 0);
        w(weights, "pace", "active", 0, 0, 2, 1);

        w(weights, "place", "cafe", 3, 0, 0, 0);
        w(weights, "place", "restaurant", 0, 3, 0, 0);
        w(weights, "place", "market", 0, 2, 1, 0);
        w(weights, "place", "culture", 0, 0, 3, 0);
        w(weights, "place", "riverside", 0, 0, 0, 3);

        w(weights, "food_interest", "low", 2, 0, 0, 0);
        w(weights, "food_interest", "medium", 0, 1, 0, 0);
        w(weights, "food_interest", "high", 0, 3, 0, 0);

        w(weights, "day_style", "light", 2, 0, 0, 0);
        w(weights, "day_style", "standard", 0, 1, 1, 0);
        w(weights, "day_style", "full", 0, 0, 2, 2);

        return weights;
    }

    private static void w(
            Map<String, Map<TravelType, Integer>> weights,
            String questionId,
            String optionId,
            int healing,
            int food,
            int experience,
            int night
    ) {
        Map<TravelType, Integer> map = new EnumMap<>(TravelType.class);
        map.put(TravelType.HEALING, healing);
        map.put(TravelType.FOOD, food);
        map.put(TravelType.EXPERIENCE, experience);
        map.put(TravelType.NIGHT, night);
        weights.put(compositeKey(questionId, optionId), map);
    }

    public static String compositeKey(String questionId, String optionId) {
        return questionId + ":" + optionId;
    }

    private static SurveyQuestionResponse question(
            String id,
            String text,
            List<SurveyOptionResponse> options
    ) {
        return new SurveyQuestionResponse(id, text, options);
    }

    private static SurveyOptionResponse option(String id, String label) {
        return new SurveyOptionResponse(id, label);
    }
}
