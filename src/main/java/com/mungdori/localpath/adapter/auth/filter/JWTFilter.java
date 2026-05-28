package com.mungdori.localpath.adapter.auth.filter;

import com.mungdori.localpath.application.auth.JWTUtil;
import com.mungdori.localpath.common.constants.AuthConstants;
import com.mungdori.localpath.common.constants.JwtClaims;
import com.mungdori.localpath.domain.auth.CustomOAuth2User;
import com.mungdori.localpath.domain.auth.OAuth2ResponseUser;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorization = request.getHeader(AuthConstants.AUTHORIZATION_HEADER);

        if (authorization == null || !authorization.startsWith(AuthConstants.BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorization.substring(AuthConstants.BEARER_PREFIX_LENGTH);

        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            PrintWriter writer = response.getWriter();
            writer.print("access token expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String category = jwtUtil.getCategory(accessToken);
        if (!JwtClaims.ACCESS.equals(category)) {
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String email = jwtUtil.getEmail(accessToken);
        String name = jwtUtil.getName(accessToken);
        String role = jwtUtil.getRole(accessToken);

        OAuth2ResponseUser user = new OAuth2ResponseUser(role, email, name);
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(user);

        Authentication authToken = new UsernamePasswordAuthenticationToken(
                customOAuth2User, null, customOAuth2User.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
