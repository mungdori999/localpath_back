package com.mungdori.localpath.adapter.auth.filter;

import com.mungdori.localpath.application.auth.JWTUtil;
import com.mungdori.localpath.common.auth.AuthCookieFactory;
import com.mungdori.localpath.common.constants.ApiPaths;
import com.mungdori.localpath.common.constants.AuthConstants;
import com.mungdori.localpath.common.constants.JwtClaims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;

public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;

    public CustomLogoutFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        if (!ApiPaths.LOGOUT.equals(request.getRequestURI()) || !"POST".equals(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        String refresh = extractRefreshCookie(request.getCookies());
        if (refresh == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (!JwtClaims.REFRESH.equals(jwtUtil.getCategory(refresh))) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        response.addCookie(AuthCookieFactory.clearRefreshCookie());
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String extractRefreshCookie(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        return Arrays.stream(cookies)
                .filter(cookie -> AuthConstants.REFRESH_COOKIE.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
