package com.mungdori.localpath.domain.auth;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record OAuth2ResponseUser(
        @NotNull
        String role,
        @Email
        String email,
        @NotNull
        String name
) {
}
