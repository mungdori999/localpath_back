package com.mungdori.localpath.adapter;

import com.mungdori.localpath.adapter.auth.filter.CustomLogoutFilter;
import com.mungdori.localpath.adapter.auth.filter.CustomSuccessHandler;
import com.mungdori.localpath.adapter.auth.filter.JWTFilter;
import com.mungdori.localpath.adapter.config.LocalpathProperties;
import com.mungdori.localpath.application.auth.CustomOAuth2UserService;
import com.mungdori.localpath.application.auth.JWTUtil;
import com.mungdori.localpath.common.constants.ApiPaths;
import com.mungdori.localpath.common.constants.AuthConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;
    private final LocalpathProperties localpathProperties;

    public SecurityConfig(
            CustomOAuth2UserService customOAuth2UserService,
            CustomSuccessHandler customSuccessHandler,
            JWTUtil jwtUtil,
            LocalpathProperties localpathProperties
    ) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customSuccessHandler = customSuccessHandler;
        this.jwtUtil = jwtUtil;
        this.localpathProperties = localpathProperties;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()));

        http.csrf((auth) -> auth.disable());
        http.formLogin((auth) -> auth.disable());
        http.httpBasic((auth) -> auth.disable());

        http.addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new CustomLogoutFilter(jwtUtil), LogoutFilter.class);

        http.oauth2Login((oauth2) -> oauth2
                .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                        .userService(customOAuth2UserService))
                .successHandler(customSuccessHandler)
        );

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers(
                        ApiPaths.AUTH_PATTERN,
                        ApiPaths.REISSUE,
                        ApiPaths.PASSES_PATTERN,
                        ApiPaths.LOGOUT
                ).permitAll()
                .requestMatchers(
                        ApiPaths.SURVEY_PATTERN,
                        ApiPaths.BADGES_PATTERN,
                        ApiPaths.VISITS_PATTERN
                ).authenticated()
                .anyRequest().authenticated());

        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(localpathProperties.getCors().getAllowedOrigins());
            configuration.setAllowedMethods(List.of("*"));
            configuration.setAllowCredentials(true);
            configuration.setAllowedHeaders(List.of("*"));
            configuration.setMaxAge(3600L);
            configuration.setExposedHeaders(List.of(
                    "Set-Cookie",
                    AuthConstants.AUTHORIZATION_HEADER,
                    AuthConstants.ACCESS_HEADER
            ));
            return configuration;
        };
    }
}
