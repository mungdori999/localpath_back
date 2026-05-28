package com.mungdori.localpath.adapter;

import com.mungdori.localpath.common.constants.Messages;
import com.mungdori.localpath.domain.auth.CustomOAuth2User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public final class AuthorizationUtil {

    private AuthorizationUtil() {
    }

    public static Optional<String> currentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomOAuth2User user) {
            return Optional.ofNullable(user.getEmail());
        }
        if (principal instanceof UserDetails userDetails) {
            return Optional.ofNullable(userDetails.getUsername());
        }
        return Optional.empty();
    }

    public static String requireEmail() {
        return currentUserEmail()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, Messages.LOGIN_REQUIRED));
    }
}
