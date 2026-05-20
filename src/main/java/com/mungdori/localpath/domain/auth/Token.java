package com.mungdori.localpath.domain.auth;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Token {
    private String accessToken;
    private String refreshToken;
}
