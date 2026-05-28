package com.mungdori.localpath.application.passes;

import com.mungdori.localpath.common.constants.PassIds;
import com.mungdori.localpath.domain.passes.CourseEntity;
import com.mungdori.localpath.domain.passes.PassEntity;
import com.mungdori.localpath.domain.passes.SpotEntity;

import java.util.List;

public class PassSeedData {
    private PassSeedData() {
    }

    public static List<PassEntity> passes() {
        PassEntity one = PassEntity.create(
                PassIds.ONE_STEP,
                "한걸음 패스",
                "망원동을 가볍게 걷는 하루",
                "1일",
                30000,
                "망원동 로컬 코스를 미리 살펴보고, 동선대로 한 걸음씩 걸어보는 입문형 패스예요.",
                "🚶"
        );

        CourseEntity oneHealing = CourseEntity.create(
                "healing",
                "힐링 추천 코스",
                "🌿",
                "조용한 카페와 분위기 좋은 식당으로 천천히 쉬어가요.",
                0
        );
        oneHealing.addSpot(SpotEntity.create(
                "어노브 ANOVE",
                "힐링 카페",
                "서울 마포구 희우정로10길 5",
                37.556447,
                126.905103,
                "망원동 대표 로스터리 카페, 창가 자리 인기",
                0
        ));
        oneHealing.addSpot(SpotEntity.create(
                "블루보틀 망원",
                "힐링 카페",
                "서울 마포구 월드컵로10길 62",
                37.555954,
                126.903684,
                "심플한 인테리어와 핸드드립 커피",
                1
        ));
        oneHealing.addSpot(SpotEntity.create(
                "옥동식",
                "분위기 식당",
                "서울 마포구 포은로 26",
                37.556851,
                126.902069,
                "망원 대표 한식당, 점심 웨이팅 있음",
                2
        ));
        one.addCourse(oneHealing);

        CourseEntity oneFood = CourseEntity.create(
                "food",
                "먹거리 추천 코스",
                "🍽️",
                "망원시장과 동네 맛집을 이어 걷는 먹방 동선이에요.",
                1
        );
        oneFood.addSpot(SpotEntity.create(
                "망원시장",
                "시장",
                "서울 마포구 망원동 414-3",
                37.556347,
                126.905681,
                "닭강정·떡볶이·빈대떡 등 시장 먹거리",
                0
        ));
        oneFood.addSpot(SpotEntity.create(
                "할매순대국 망원본점",
                "맛집",
                "서울 마포구 망원로 59",
                37.556102,
                126.904891,
                "얼큰 순대국으로 유명한 망원 스테디",
                1
        ));
        oneFood.addSpot(SpotEntity.create(
                "진미식당",
                "맛집",
                "서울 마포구 망원로10길 7",
                37.556218,
                126.904512,
                "동파육·짜장면으로 오래 사랑받은 중식당",
                2
        ));
        one.addCourse(oneFood);

        PassEntity two = PassEntity.create(
                PassIds.TWO_STEP,
                "두걸음 패스",
                "망원동을 깊게 즐기는 하루",
                "1일",
                50000,
                "한걸음 패스에 체험·야경 코스까지 더해진 프리미엄 패스예요.",
                "🚶‍♂️"
        );

        CourseEntity twoHealing = CourseEntity.create(
                "healing",
                "힐링 추천 코스",
                "🌿",
                "카페와 식당, 브런치 스팟까지 여유롭게 돌아요.",
                0
        );
        twoHealing.addSpot(SpotEntity.create(
                "어노브 ANOVE",
                "힐링 카페",
                "서울 마포구 희우정로10길 5",
                37.556447,
                126.905103,
                "망원동 대표 로스터리 카페",
                0
        ));
        twoHealing.addSpot(SpotEntity.create(
                "블루보틀 망원",
                "힐링 카페",
                "서울 마포구 월드컵로10길 62",
                37.555954,
                126.903684,
                "핸드드립·원두 구매 가능",
                1
        ));
        twoHealing.addSpot(SpotEntity.create(
                "카페모토",
                "힐링 카페",
                "서울 마포구 포은로8길 57",
                37.556308,
                126.901421,
                "공간이 넓고 디저트가 인기",
                2
        ));
        twoHealing.addSpot(SpotEntity.create(
                "옥동식",
                "분위기 식당",
                "서울 마포구 포은로 26",
                37.556851,
                126.902069,
                "망원 대표 한식 코스요리",
                3
        ));
        two.addCourse(twoHealing);

        CourseEntity twoFood = CourseEntity.create(
                "food",
                "먹거리 추천 코스",
                "🍽️",
                "시장부터 골목 맛집까지 망원 먹거리 풀코스예요.",
                1
        );
        twoFood.addSpot(SpotEntity.create(
                "망원시장",
                "시장",
                "서울 마포구 망원동 414-3",
                37.556347,
                126.905681,
                "시장 안 먹거리 골목부터 시작",
                0
        ));
        twoFood.addSpot(SpotEntity.create(
                "할매순대국 망원본점",
                "맛집",
                "서울 마포구 망원로 59",
                37.556102,
                126.904891,
                "순대국·뼈해장국",
                1
        ));
        twoFood.addSpot(SpotEntity.create(
                "진미식당",
                "맛집",
                "서울 마포구 망원로10길 7",
                37.556218,
                126.904512,
                "중식 런치 코스 추천",
                2
        ));
        twoFood.addSpot(SpotEntity.create(
                "오향족발 망원점",
                "맛집",
                "서울 마포구 망원로6길 15",
                37.555891,
                126.904982,
                "저녁에 즐기는 족발·보쌈",
                3
        ));
        two.addCourse(twoFood);

        CourseEntity twoExperience = CourseEntity.create(
                "experience",
                "체험 추천 코스",
                "🎨",
                "시장과 문화공간을 걸으며 망원 로컬 문화를 느껴요.",
                2
        );
        twoExperience.addSpot(SpotEntity.create(
                "망원시장",
                "마켓",
                "서울 마포구 망원동 414-3",
                37.556347,
                126.905681,
                "골목 골목 시장 산책",
                0
        ));
        twoExperience.addSpot(SpotEntity.create(
                "문화비축창",
                "문화공간",
                "서울 마포구 월드컵로1길 19",
                37.554198,
                126.9015,
                "전시·공연, 망원동 대표 문화 스팟",
                1
        ));
        two.addCourse(twoExperience);

        CourseEntity twoNight = CourseEntity.create(
                "night",
                "야경 추천 코스",
                "🌙",
                "저녁 이후 한강과 나들목 야경을 즐기는 동선이에요.",
                3
        );
        twoNight.addSpot(SpotEntity.create(
                "망원한강공원",
                "야경",
                "서울 마포구 망원동 221-8",
                37.555134,
                126.894466,
                "한강 노을·야경 산책",
                0
        ));
        twoNight.addSpot(SpotEntity.create(
                "망원나들목",
                "야경",
                "서울 마포구 망원동 450-7",
                37.554512,
                126.896218,
                "나들목 뷰포인트, 사진 명소",
                1
        ));
        two.addCourse(twoNight);

        return List.of(one, two);
    }
}

