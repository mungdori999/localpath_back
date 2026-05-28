package com.mungdori.localpath.adapter.auth.filter;

import com.mungdori.localpath.adapter.config.LocalpathProperties;
import com.mungdori.localpath.application.auth.JWTUtil;
import com.mungdori.localpath.common.auth.AuthCookieFactory;
import com.mungdori.localpath.common.constants.JwtClaims;
import com.mungdori.localpath.domain.auth.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final LocalpathProperties localpathProperties;

    @Value("${spring.jwt.accessToken-expire-length}")
    private long accessExpireLong;

    @Value("${spring.jwt.refresh-expire-length}")
    private long refreshExpireLong;

    public CustomSuccessHandler(JWTUtil jwtUtil, LocalpathProperties localpathProperties) {
        this.jwtUtil = jwtUtil;
        this.localpathProperties = localpathProperties;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String name = customUserDetails.getName();
        String email = customUserDetails.getEmail();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        String access = jwtUtil.createJwt(JwtClaims.ACCESS, name, role, email, accessExpireLong);
        String refresh = jwtUtil.createJwt(JwtClaims.REFRESH, name, role, email, refreshExpireLong);

        response.addCookie(AuthCookieFactory.refreshCookie(refresh, (int) refreshExpireLong));
        response.sendRedirect(localpathProperties.getFrontend().oauthRedirectUrl(access));
    }
}
