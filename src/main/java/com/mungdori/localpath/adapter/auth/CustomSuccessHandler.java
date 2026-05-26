package com.mungdori.localpath.adapter.auth;

import com.mungdori.localpath.application.auth.JWTUtil;
import com.mungdori.localpath.domain.auth.CustomOAuth2User;
import com.mungdori.localpath.domain.auth.Token;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    @Value("${spring.jwt.accessToken-expire-length}")
    private long accessExpireLong;
    @Value("${spring.jwt.refresh-expire-length}")
    private long refreshExpireLong;

    public CustomSuccessHandler(JWTUtil jwtUtil1) {
        this.jwtUtil = jwtUtil1;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String name = customUserDetails.getName();
        String email = customUserDetails.getEmail();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();


        String access = jwtUtil.createJwt("access", name, role, email, accessExpireLong);
        String refresh = jwtUtil.createJwt("refresh", name, role, email, refreshExpireLong);

        response.addCookie(createCookie("refresh", refresh));
        response.sendRedirect(
                "http://localhost:5173?access=" + access
        );
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
