package com.mungdori.localpath.common.auth;

import com.mungdori.localpath.adapter.config.LocalpathProperties;
import com.mungdori.localpath.common.constants.AuthConstants;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthCookieWriter {

    private final LocalpathProperties localpathProperties;

    public void addRefreshCookie(HttpServletResponse response, String value, int maxAgeSeconds) {
        LocalpathProperties.Auth auth = localpathProperties.getAuth();

        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(
                        AuthConstants.REFRESH_COOKIE,
                        value != null ? value : ""
                )
                .path(AuthConstants.COOKIE_PATH_ROOT)
                .httpOnly(true)
                .maxAge(maxAgeSeconds)
                .secure(auth.isCookieSecure())
                .sameSite(auth.getCookieSameSite());

        if (auth.getCookieDomain() != null && !auth.getCookieDomain().isBlank()) {
            builder.domain(auth.getCookieDomain());
        }

        response.addHeader(HttpHeaders.SET_COOKIE, builder.build().toString());
    }

    public void clearRefreshCookie(HttpServletResponse response) {
        addRefreshCookie(response, "", 0);
    }
}
