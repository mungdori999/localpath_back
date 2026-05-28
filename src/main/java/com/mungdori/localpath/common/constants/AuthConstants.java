package com.mungdori.localpath.common.constants;

public final class AuthConstants {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String OAUTH_PROVIDER_KAKAO = "kakao";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

    public static final String ACCESS_HEADER = "access";
    public static final String REFRESH_COOKIE = "refresh";
    public static final String COOKIE_PATH_ROOT = "/";

    public static final String OAUTH_ACCESS_QUERY_PARAM = "access";

    private AuthConstants() {
    }
}
