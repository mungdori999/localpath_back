package com.mungdori.localpath.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {
    String accessToken;
    String refreshToken;


}
