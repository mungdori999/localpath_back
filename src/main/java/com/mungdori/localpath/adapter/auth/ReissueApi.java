package com.mungdori.localpath.adapter.auth;

import com.mungdori.localpath.application.auth.JWTUtil;
import com.mungdori.localpath.common.auth.AuthCookieFactory;
import com.mungdori.localpath.common.constants.ApiPaths;
import com.mungdori.localpath.common.constants.AuthConstants;
import com.mungdori.localpath.common.constants.JwtClaims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiPaths.API)
public class ReissueApi {

    private final JWTUtil jwtUtil;

    @Value("${spring.jwt.accessToken-expire-length}")
    private long accessExpireLong;

    @Value("${spring.jwt.refresh-expire-length}")
    private long refreshExpireLong;

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        String refresh = extractRefreshCookie(request.getCookies());
        if (refresh == null) {
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        if (!JwtClaims.REFRESH.equals(jwtUtil.getCategory(refresh))) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        String name = jwtUtil.getName(refresh);
        String role = jwtUtil.getRole(refresh);
        String email = jwtUtil.getEmail(refresh);

        String newAccess = jwtUtil.createJwt(JwtClaims.ACCESS, name, role, email, accessExpireLong);
        String newRefresh = jwtUtil.createJwt(JwtClaims.REFRESH, name, role, email, refreshExpireLong);

        response.setHeader(AuthConstants.ACCESS_HEADER, newAccess);
        response.addCookie(AuthCookieFactory.refreshCookie(newRefresh, (int) refreshExpireLong));
        return ResponseEntity.ok(Map.of(JwtClaims.ACCESS_TOKEN_RESPONSE_KEY, newAccess));
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
