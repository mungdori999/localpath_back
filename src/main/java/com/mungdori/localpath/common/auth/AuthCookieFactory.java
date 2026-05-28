package com.mungdori.localpath.common.auth;

import com.mungdori.localpath.common.constants.AuthConstants;
import jakarta.servlet.http.Cookie;

public final class AuthCookieFactory {

    private AuthCookieFactory() {
    }

    public static Cookie refreshCookie(String value, int maxAgeSeconds) {
        Cookie cookie = new Cookie(AuthConstants.REFRESH_COOKIE, value);
        cookie.setMaxAge(maxAgeSeconds);
        cookie.setPath(AuthConstants.COOKIE_PATH_ROOT);
        cookie.setHttpOnly(true);
        return cookie;
    }

    public static Cookie clearRefreshCookie() {
        return refreshCookie(null, 0);
    }
}
