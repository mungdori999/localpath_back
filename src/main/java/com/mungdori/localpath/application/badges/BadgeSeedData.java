package com.mungdori.localpath.application.badges;

import com.mungdori.localpath.domain.badges.Badge;

import java.util.List;

public final class BadgeSeedData {

    private BadgeSeedData() {
    }

    public static List<Badge> badges() {
        Badge mangridanCafe = Badge.create(
                "mangridan-cafe-master",
                "망리단길 카페 마스터",
                "망리단길의 대표 카페 2곳을 방문하고 인증하세요",
                "☕",
                "망리단길",
                0
        );
        mangridanCafe.addRequirement("어노브 ANOVE");
        mangridanCafe.addRequirement("블루보틀 망원");

        Badge mangridanFood = Badge.create(
                "mangridan-foodie",
                "망리단길 미식가",
                "망리단길 분위기 좋은 식당을 방문하고 인증하세요",
                "🍽️",
                "망리단길",
                1
        );
        mangridanFood.addRequirement("옥동식");

        Badge mangridanHealing = Badge.create(
                "mangridan-healing",
                "망리단길 힐링러",
                "망리단길 카페와 식당을 모두 방문하고 인증하세요",
                "🌿",
                "망리단길",
                2
        );
        mangridanHealing.addRequirement("카페모토");
        mangridanHealing.addRequirement("옥동식");

        Badge mangwonMarket = Badge.create(
                "mangwon-market-explorer",
                "망원시장 탐험가",
                "망원시장에 방문하고 인증하세요",
                "🏪",
                "망원시장",
                3
        );
        mangwonMarket.addRequirement("망원시장");

        Badge mangwonFood = Badge.create(
                "mangwon-food-master",
                "망원 미식가",
                "망원시장과 동네 맛집 2곳을 방문하고 인증하세요",
                "🥘",
                "망원시장",
                4
        );
        mangwonFood.addRequirement("망원시장");
        mangwonFood.addRequirement("할매순대국 망원본점");
        mangwonFood.addRequirement("진미식당");

        Badge mangwonNight = Badge.create(
                "mangwon-night-walker",
                "망원 야경러",
                "망원 한강 야경 명소 2곳을 방문하고 인증하세요",
                "🌙",
                "망원",
                5
        );
        mangwonNight.addRequirement("망원한강공원");
        mangwonNight.addRequirement("망원나들목");

        Badge mangwonCulture = Badge.create(
                "mangwon-culture",
                "망원 문화 탐방",
                "망원시장과 문화비축창을 방문하고 인증하세요",
                "🎨",
                "망원",
                6
        );
        mangwonCulture.addRequirement("망원시장");
        mangwonCulture.addRequirement("문화비축창");

        return List.of(
                mangridanCafe,
                mangridanFood,
                mangridanHealing,
                mangwonMarket,
                mangwonFood,
                mangwonNight,
                mangwonCulture
        );
    }
}
