package com.mungdori.localpath.domain.survey;

import java.util.Arrays;
import java.util.Optional;

public enum TravelType {
    HEALING("healing", "힐링형", "조용한 카페와 여유로운 식사를 즐기는 타입이에요"),
    FOOD("food", "먹거리형", "맛집과 시장을 중심으로 도는 타입이에요"),
    EXPERIENCE("experience", "체험형", "문화·로컬 체험을 좋아하는 타입이에요"),
    NIGHT("night", "야경형", "한강·야경 산책을 즐기는 타입이에요");

    private final String courseKey;
    private final String label;
    private final String description;

    TravelType(String courseKey, String label, String description) {
        this.courseKey = courseKey;
        this.label = label;
        this.description = description;
    }

    public String courseKey() {
        return courseKey;
    }

    public String label() {
        return label;
    }

    public String description() {
        return description;
    }

    public static Optional<TravelType> fromCourseKey(String courseKey) {
        return Arrays.stream(values())
                .filter(t -> t.courseKey.equals(courseKey))
                .findFirst();
    }
}
