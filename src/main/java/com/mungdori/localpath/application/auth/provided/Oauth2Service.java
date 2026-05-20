package com.mungdori.localpath.application.auth.provided;

import com.mungdori.localpath.domain.auth.LoginOAuth2;

public interface Oauth2Service {
    LoginOAuth2 getInfo(String accessToken);
}
