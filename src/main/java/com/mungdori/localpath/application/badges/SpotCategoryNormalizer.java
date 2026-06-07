package com.mungdori.localpath.application.badges;

public final class SpotCategoryNormalizer {

    private SpotCategoryNormalizer() {
    }

    /** 업종 다양성 판정용 — 비슷한 카테고리를 하나로 묶음 */
    public static String normalize(String category) {
        if (category == null || category.isBlank()) {
            return "기타";
        }
        return switch (category.trim()) {
            case "힐링 카페" -> "카페";
            case "분위기 식당", "맛집" -> "식당";
            case "시장", "마켓" -> "시장·먹거리";
            case "문화공간" -> "문화·체험";
            case "서점" -> "서점";
            case "야경" -> "야경·명소";
            default -> category.trim();
        };
    }
}
