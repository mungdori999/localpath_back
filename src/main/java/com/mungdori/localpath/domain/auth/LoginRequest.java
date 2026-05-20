package com.mungdori.localpath.domain.auth;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull
        String accessToken
) {
}
