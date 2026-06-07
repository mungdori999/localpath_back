package com.mungdori.localpath.application.badges;

import com.mungdori.localpath.domain.badges.Badge;

import java.util.List;

public final class BadgeSeedData {

    public static final String DEPRECATED_EXPLORER_KEY = "mangwon-local-explorer";
    public static final String WELCOME_BADGE_IMAGE = "/badges/welcome-member.png";

    private BadgeSeedData() {
    }

    public static List<Badge> badges() {
        Badge welcome = Badge.create(
                "welcome-member",
                "가입 축하",
                "로컬패스에 오신 것을 환영합니다",
                null,
                WELCOME_BADGE_IMAGE,
                "전체",
                -1,
                false
        );

        return List.of(
                welcome,
                visitBadge(
                        "mangridan-cafe-master",
                        "망리단길 카페러",
                        "망리단길 대표 카페 2곳을 방문하고 인증하세요",
                        "☕",
                        "/badges/mangridan-cafe-master.png",
                        "망리단길",
                        0,
                        "어노브 ANOVE - 망리단길점",
                        "블루보틀 - 망리단길점"
                ),
                visitBadge(
                        "mangridan-foodie",
                        "망리단길 미식가",
                        "망리단길 인기 식당 2곳을 방문하고 인증하세요",
                        "🍽️",
                        "/badges/mangridan-foodie.png",
                        "망리단길",
                        1,
                        "옥동식 - 망리단길점",
                        "삼봉식당 - 망리단길점"
                ),
                visitBadge(
                        "mangwon-food-master",
                        "망원 맛집 탐험",
                        "망원 동네 맛집 3곳을 방문하고 인증하세요",
                        "🥘",
                        "/badges/mangwon-food-master.png",
                        "망원시장",
                        2,
                        "할매순대국 - 망원시장점",
                        "진미식당 - 망원점",
                        "오향족발 - 망원시장점"
                ),
                visitBadge(
                        "mangwon-market-explorer",
                        "망원 체험 탐방",
                        "시장 먹거리와 문화공간 2곳을 방문하고 인증하세요",
                        "🎨",
                        "/badges/mangwon-market-explorer.png",
                        "망원",
                        3,
                        "호돌이 닭강정 - 망원시장점",
                        "문화비축창 - 망원점"
                ),
                visitBadge(
                        "mangridan-healing",
                        "망원 골목 믹스",
                        "카페·식당·서점 등 서로 다른 업종 3곳을 방문하고 인증하세요",
                        "🗺️",
                        "/badges/mangridan-healing.png",
                        "망원동",
                        4,
                        "카페모토 - 망리단길점",
                        "연어삼촌 - 망리단길점",
                        "망원책방 노트 - 망원점"
                ),
                visitBadge(
                        "mangwon-night-walker",
                        "망원 야경 산책",
                        "한강·나들목 야경 명소 2곳을 방문하고 인증하세요",
                        "🌙",
                        "/badges/mangwon-night-walker.png",
                        "망원",
                        5,
                        "망원한강공원",
                        "망원나들목"
                ),
                visitBadge(
                        "mangwon-culture",
                        "망원 로컬 믹스",
                        "카페·시장·문화공간 등 다양한 업종 3곳을 방문하고 인증하세요",
                        "✨",
                        "/badges/mangwon-culture.png",
                        "망원",
                        6,
                        "블루보틀 - 망리단길점",
                        "최네집 떡볶이 - 망원시장점",
                        "문화비축창 - 망원점"
                )
        );
    }

    private static Badge visitBadge(
            String badgeKey,
            String name,
            String description,
            String emoji,
            String image,
            String region,
            int orderIndex,
            String... spots
    ) {
        Badge badge = Badge.create(badgeKey, name, description, emoji, image, region, orderIndex, true);
        for (String spot : spots) {
            badge.addRequirement(spot);
        }
        return badge;
    }
}
