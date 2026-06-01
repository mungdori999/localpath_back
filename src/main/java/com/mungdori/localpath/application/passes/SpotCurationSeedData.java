package com.mungdori.localpath.application.passes;

import com.mungdori.localpath.domain.passes.SpotCuration;

import java.util.List;

public final class SpotCurationSeedData {

    private static final String OWNER_TITLE = "망원동 사장님 추천";

    private SpotCurationSeedData() {
    }

    public static List<SpotCuration> curations() {
        return List.of(
                c("어노브 ANOVE",
                        "시그니처 핸드드립 · 시즌 디저트",
                        "평일 오전 10~12시 (웨이팅 적음)",
                        "2층 창가 자리는 햇빛이 좋아요. 원두는 200g 소분 패키지가 가성비 최고입니다."),
                c("블루보틀 망원",
                        "블렌드 No.1 · 카페 라떼",
                        "주말 오후 2시 전",
                        "테이크아웃 줄이 길면 키오스크 주문이 더 빨라요. 매장 뒤쪽 좌석이 조용합니다."),
                c("옥동식",
                        "한우 떡갈비 정식 · 점심 특선",
                        "평일 점심 11시 30분 오픈 직후",
                        "예약 없이 가면 11시 40분 전 도착을 추천해요. 반찬 리필은 부담 없이 요청하세요."),
                c("망원시장",
                        "떡볶이 · 닭강정 · 빈대떡",
                        "평일 오후 3~5시 (한산한 시간)",
                        "시장 안쪽 골목이 관광객보다 현지인 비율이 높아요. 현금 준비해 두면 결제가 빠릅니다."),
                c("할매순대국 망원본점",
                        "얼큰 순대국 · 뼈해장국",
                        "아침 8~9시 또는 저녁 6시 이후",
                        "기본 양이 넉넉해요. 순대 추가는 1인 1개면 충분합니다."),
                c("진미식당",
                        "동파육 · 짜장면",
                        "평일 런치 12시 전",
                        "동파육은 2인 이상 주문 시 나눠 먹기 좋아요. 테이블 웨이팅보다 카운터 대기가 빠를 때가 많아요."),
                c("카페모토",
                        "크림 라떼 · 휘낭시에",
                        "주말 브런치 10~11시",
                        "1층은 반려견 동반 가능해요. 디저트는 오후에 품절되는 경우가 많습니다."),
                c("오향족발 망원점",
                        "마늘족발 · 모듬 보쌈",
                        "저녁 6시 30분~8시",
                        "족발은 중간 맛이 가장 인기 있어요. 남은 고기는 볶음밥으로 마무리 추천합니다."),
                c("문화비축창",
                        "기획 전시 · 야외 공연 (시즌별)",
                        "주말 오후 2~5시",
                        "입장 전 홈페이지에서 당일 무료 프로그램을 확인하세요. 노을 시간대 정원 산책이 특히 좋습니다."),
                c("망원한강공원",
                        "야경 산책 · 피크닉",
                        "일몰 30분 전 (계절별 17~19시)",
                        "자전거 대여는 북쪽 입구 근처가 한산해요. 바람이 강한 날은 나들목 쪽보다 공원 안쪽이 낫습니다."),
                c("망원나들목",
                        "한강 뷰 포토스팟",
                        "해 질 녘 ~ 밤 9시",
                        "사진은 나들목 중앙 계단 위쪽 전망대가 각이 잘 나와요. 주말 밤에는 삼각대 자리를 일찍 잡으세요.")
        );
    }

    private static SpotCuration c(
            String spotName,
            String signatureMenu,
            String visitTime,
            String tip
    ) {
        return SpotCuration.create(spotName, OWNER_TITLE, signatureMenu, visitTime, tip);
    }
}
