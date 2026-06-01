package com.mungdori.localpath.common.time;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * 서비스 전반의 "현재 시각"·만료 계산은 한국(Asia/Seoul) 기준으로 통일합니다.
 */
public final class KoreaTime {

    public static final ZoneId ZONE = ZoneId.of("Asia/Seoul");

    private KoreaTime() {
    }

    public static LocalDateTime nowLocal() {
        return LocalDateTime.now(ZONE);
    }

    public static OffsetDateTime toOffset(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(ZONE).toOffsetDateTime();
    }
}
