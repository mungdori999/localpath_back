package com.mungdori.localpath.common.constants;

public final class Messages {

    public static final String LOGIN_REQUIRED = "로그인이 필요합니다";
    public static final String MEMBER_NOT_FOUND = "회원을 찾을 수 없습니다";
    public static final String PASS_DATA_NOT_FOUND = "패스 데이터 없음";
    public static final String RECOMMENDED_PASS_NOT_FOUND = "추천 패스 없음";
    public static final String DEFAULT_COURSE_NAME = "추천 코스";

    public static final String SURVEY_ANSWERS_EMPTY = "답변이 비어 있습니다";
    public static final String SURVEY_ANSWER_REQUIRED_PREFIX = "모든 문항에 답해 주세요: ";
    public static final String SCORE_PARSE_FAILED = "점수 파싱 실패";
    public static final String JSON_CONVERT_FAILED = "JSON 변환 실패";

    public static final String RECOMMENDATION_REASON =
            "%s 성향에 맞춰 %s의 「%s」를 추천해요.";
    public static final String RECOMMENDATION_REASON_ADVENTURE =
            "%s 성향에 맞춰 %s의 「%s」를 추천해요. 체험·야경까지 즐길 수 있는 동선이에요.";

    public static final String SPOT_NAME_REQUIRED = "장소 이름이 필요합니다";
    public static final String SPOT_NOT_FOUND = "장소를 찾을 수 없습니다";
    public static final String VISIT_TOO_FAR = "장소 근처에서만 인증할 수 있습니다";

    private Messages() {
    }
}
